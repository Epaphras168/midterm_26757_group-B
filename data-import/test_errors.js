const { Provinces, Districts, Sectors, Cells, Villages } = require('rwanda');

try {
    for (const p of Provinces()) {
        const dists = Districts(p) || [];
        for (const d of dists) {
            let secs;
            try { secs = Sectors(p, d); } catch (e) { console.error(`Error on Sectors(${p}, ${d})`); continue; }
            for (const s of secs || []) {
                let cells;
                try { cells = Cells(p, d, s); } catch (e) { console.error(`Error on Cells(${p}, ${d}, ${s})`); continue; }
                for (const c of cells || []) {
                    let vils;
                    try { vils = Villages(p, d, s, c); } catch (e) { console.error(`Error on Villages(${p}, ${d}, ${s}, ${c})`); continue; }
                }
            }
        }
    }
    console.log("Success parsing all");
} catch (e) {
    console.error("Top level error:", e);
}
