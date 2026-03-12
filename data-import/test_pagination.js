async function test() {
    console.log("Testing provinces pagination (page 0, size 2)");
    const res1 = await fetch('http://localhost:8080/api/locations/provinces?page=0&size=2');
    const data1 = await res1.json();
    console.log(`Received page 0: ${data1.content ? data1.content.length : 'N/A'} items`);
    console.log(`Total elements: ${data1.totalElements}`);
    console.log(`Total pages: ${data1.totalPages}`);

    console.log("\nTesting children pagination (Gasabo ID 6, page 0, size 5)");
    const res2 = await fetch('http://localhost:8080/api/locations/6/children?page=0&size=5');
    const data2 = await res2.json();
    console.log(`Received page 0: ${data2.content ? data2.content.length : 'N/A'} items`);
    console.log(`Total elements: ${data2.totalElements}`);
    console.log(`Total pages: ${data2.totalPages}`);
}

test().catch(console.error);
