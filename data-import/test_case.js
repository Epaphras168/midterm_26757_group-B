const geo = require('rwanda-geo');
console.log("Exports:", Object.keys(geo));
try {
    console.log("Provinces:", geo.getProvinces ? geo.getProvinces() : "N/A");
    console.log("Districts for Kigali:", geo.getDistricts ? geo.getDistricts('Kigali') : "N/A");
} catch (e) {
    console.log(e);
}
