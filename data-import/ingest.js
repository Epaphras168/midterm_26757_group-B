const fs = require('fs');

async function ingestData() {
    console.log("Reading raw JSON data...");
    const raw = JSON.parse(fs.readFileSync('locations.json', 'utf8'));

    // Build tree
    const tree = {};
    for (const row of raw) {
        const p = row.province_name;
        const d = row.district_name;
        const s = row.sector_name;
        const c = row.cell_name;
        const v = row.village_name;

        if (!tree[p]) tree[p] = {};
        if (!tree[p][d]) tree[p][d] = {};
        if (!tree[p][d][s]) tree[p][d][s] = {};
        if (!tree[p][d][s][c]) tree[p][d][s][c] = {};
        tree[p][d][s][c][v] = true;
    }

    const levels = {
        PROVINCES: [],
        DISTRICTS: [],
        SECTORS: [],
        CELLS: [],
        VILLAGES: []
    };

    let pIdx = 1;
    for (const p of Object.keys(tree).sort()) {
        const pCode = String(pIdx++);
        levels.PROVINCES.push({ name: p, code: pCode, type: 'PROVINCE', parent: null });

        let dIdx = 1;
        for (const d of Object.keys(tree[p]).sort()) {
            const dCode = pCode + String(dIdx++);
            levels.DISTRICTS.push({ name: d, code: dCode, type: 'DISTRICT', parent: { code: pCode } });

            let sIdx = 1;
            for (const s of Object.keys(tree[p][d]).sort()) {
                const sCode = dCode + String(sIdx++).padStart(2, '0');
                levels.SECTORS.push({ name: s, code: sCode, type: 'SECTOR', parent: { code: dCode } });

                let cIdx = 1;
                for (const c of Object.keys(tree[p][d][s]).sort()) {
                    const cCode = sCode + String(cIdx++).padStart(2, '0');
                    levels.CELLS.push({ name: c, code: cCode, type: 'CELL', parent: { code: sCode } });

                    let vIdx = 1;
                    for (const v of Object.keys(tree[p][d][s][c]).sort()) {
                        const vCode = cCode + String(vIdx++).padStart(2, '0');
                        levels.VILLAGES.push({ name: v, code: vCode, type: 'VILLAGE', parent: { code: cCode } });
                    }
                }
            }
        }
    }

    console.log(`Prepared collections:
    Provinces: ${levels.PROVINCES.length}
    Districts: ${levels.DISTRICTS.length}
    Sectors: ${levels.SECTORS.length}
    Cells: ${levels.CELLS.length}
    Villages: ${levels.VILLAGES.length}
    `);

    const BATCH_SIZE = 1000;

    for (let layer of ['PROVINCES', 'DISTRICTS', 'SECTORS', 'CELLS', 'VILLAGES']) {
        const items = levels[layer];
        console.log(`>>> Sending ${layer} (${items.length} items)...`);

        for (let i = 0; i < items.length; i += BATCH_SIZE) {
            const batch = items.slice(i, i + BATCH_SIZE);
            console.log(`    Batch ${Math.floor(i / BATCH_SIZE) + 1} of ${Math.ceil(items.length / BATCH_SIZE)}`);

            try {
                const response = await fetch('http://localhost:8080/api/locations/batch', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(batch)
                });

                if (!response.ok) {
                    const text = await response.text();
                    throw new Error(`HTTP ${response.status}: ${text}`);
                }
            } catch (error) {
                console.error('Error sending batch:', error.message || error);
                process.exit(1);
            }
        }
    }

    console.log('Ingestion completed successfully.');
}

ingestData().catch(console.error);
