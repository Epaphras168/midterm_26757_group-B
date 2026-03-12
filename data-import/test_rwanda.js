const { Provinces, Districts, Sectors, Cells, Villages } = require('rwanda');
let maxV = 0;
let maxLength = 0;
let pIdx = 1;
for (let p of Provinces()) {
    let pCode = String(pIdx++);
    let dIdx = 1;
    for (let d of Districts(p) || []) {
        let dCode = pCode + String(dIdx++);
        let sIdx = 1;
        for (let s of Sectors(p, d) || []) {
            let sCode = dCode + String(sIdx++).padStart(2, '0');
            let cIdx = 1;
            for (let c of Cells(p, d, s) || []) {
                let cCode = sCode + String(cIdx++).padStart(2, '0');
                let vIdx = 1;
                for (let v of Villages(p, d, s, c) || []) {
                    let vCode = cCode + String(vIdx++).padStart(2, '0');
                    if (vCode.length > maxLength) maxLength = vCode.length;
                    maxV = Math.max(maxV, vIdx - 1);
                }
            }
        }
    }
}
console.log("Max Code Length:", maxLength);
console.log("Max Villages in a cell:", maxV);
