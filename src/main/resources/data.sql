-- 1. PROVINCE (parent_id is NULL)
INSERT INTO locations (parent_id, code, "type", name) 
VALUES (NULL, 'KIGALI01', 'PROVINCE', 'Kigali City') ON CONFLICT DO NOTHING;

-- 2. DISTRICT 
INSERT INTO locations (parent_id, code, "type", name) 
VALUES ('KIGALI01', 'GASABO01', 'DISTRICT', 'Gasabo') ON CONFLICT DO NOTHING;

-- 3. SECTOR 
INSERT INTO locations (parent_id, code, "type", name) 
VALUES ('GASABO01', 'REMERA01', 'SECTOR', 'Remera') ON CONFLICT DO NOTHING;

-- 4. CELL 
INSERT INTO locations (parent_id, code, "type", name) 
VALUES ('REMERA01', 'RUKIRI01', 'CELL', 'Rukiri I') ON CONFLICT DO NOTHING;

-- 5. VILLAGE 
INSERT INTO locations (parent_id, code, "type", name) 
VALUES ('RUKIRI01', 'AMAHOR01', 'VILLAGE', 'Amahoro') ON CONFLICT DO NOTHING;


-- Insert Amenities
INSERT INTO amenities (id, name) VALUES (1, 'WiFi') ON CONFLICT DO NOTHING;
INSERT INTO amenities (id, name) VALUES (2, 'Swimming Pool') ON CONFLICT DO NOTHING;
INSERT INTO amenities (id, name) VALUES (3, 'Air Conditioning') ON CONFLICT DO NOTHING;
INSERT INTO amenities (id, name) VALUES (4, 'Parking') ON CONFLICT DO NOTHING;
INSERT INTO amenities (id, name) VALUES (5, 'Balcony') ON CONFLICT DO NOTHING;

-- Insert Users (Landlords and Tenants)
-- Note: Assuming passwords would ideally be hashed, but keeping it simple for sample data.
INSERT INTO users (id, name, email, password, role) VALUES (1, 'John Doe', 'john.landlord@example.com', 'password123', 'LANDLORD') ON CONFLICT DO NOTHING;
INSERT INTO users (id, name, email, password, role) VALUES (2, 'Jane Smith', 'jane.tenant@example.com', 'password123', 'TENANT') ON CONFLICT DO NOTHING;
INSERT INTO users (id, name, email, password, role) VALUES (3, 'Alice Johnson', 'alice.landlord@example.com', 'password123', 'LANDLORD') ON CONFLICT DO NOTHING;

-- Insert Properties
INSERT INTO properties (id, title, description, price, bedrooms, bathrooms, area, landlord_id, location_id) 
VALUES (1, 'Cozy 2BHK in Gasabo', 'A beautiful and cozy 2-bedroom apartment with modern finishings.', 450000.00, 2, 2, 85.5, 1, 2) ON CONFLICT DO NOTHING;

INSERT INTO properties (id, title, description, price, bedrooms, bathrooms, area, landlord_id, location_id) 
VALUES (2, 'Luxury Villa in Nyarugenge', 'Spacious 4-bedroom villa with a private garden and secure parking.', 1200000.00, 4, 3, 250.0, 3, 4) ON CONFLICT DO NOTHING;

INSERT INTO properties (id, title, description, price, bedrooms, bathrooms, area, landlord_id, location_id) 
VALUES (3, 'Affordable Studio in Musanze', 'Perfect studio apartment for a single professional near the city center.', 150000.00, 1, 1, 40.0, 1, 5) ON CONFLICT DO NOTHING;

-- Link Properties with Amenities
INSERT INTO property_amenities (property_id, amenity_id) VALUES (1, 1) ON CONFLICT DO NOTHING; -- WiFi
INSERT INTO property_amenities (property_id, amenity_id) VALUES (1, 4) ON CONFLICT DO NOTHING; -- Parking
INSERT INTO property_amenities (property_id, amenity_id) VALUES (1, 5) ON CONFLICT DO NOTHING; -- Balcony

INSERT INTO property_amenities (property_id, amenity_id) VALUES (2, 1) ON CONFLICT DO NOTHING; -- WiFi
INSERT INTO property_amenities (property_id, amenity_id) VALUES (2, 2) ON CONFLICT DO NOTHING; -- Swimming Pool
INSERT INTO property_amenities (property_id, amenity_id) VALUES (2, 3) ON CONFLICT DO NOTHING; -- AC
INSERT INTO property_amenities (property_id, amenity_id) VALUES (2, 4) ON CONFLICT DO NOTHING; -- Parking

INSERT INTO property_amenities (property_id, amenity_id) VALUES (3, 1) ON CONFLICT DO NOTHING; -- WiFi

-- Insert Bookings
INSERT INTO booking (id, start_date, end_date, status, tenant_id, property_id) 
VALUES (1, '2026-04-01', '2026-10-01', 'CONFIRMED', 2, 1) ON CONFLICT DO NOTHING;

INSERT INTO booking (id, start_date, end_date, status, tenant_id, property_id) 
VALUES (2, '2026-05-15', '2026-05-20', 'PENDING', 2, 3) ON CONFLICT DO NOTHING;

-- Insert Reviews
INSERT INTO review (id, rating, comment, booking_id) 
VALUES (1, 5, 'Absolutely loved the place! The landlord was very responsive and the apartment was incredibly clean.', 1) ON CONFLICT DO NOTHING;

-- Reset sequence for tables using GenerationType.IDENTITY so future inserts don't conflict with our manual IDs
SELECT setval('locations_id_seq', (SELECT MAX(id) FROM locations));
SELECT setval('amenities_id_seq', (SELECT MAX(id) FROM amenities));
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
SELECT setval('properties_id_seq', (SELECT MAX(id) FROM properties));
SELECT setval('booking_id_seq', (SELECT MAX(id) FROM booking));
SELECT setval('review_id_seq', (SELECT MAX(id) FROM review));
