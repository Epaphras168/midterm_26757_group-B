# House Rental System API Documentation

Welcome to the API documentation for the **House Rental System**. The platform facilitates interactions between tenants and landlords for property rentals across different locations.

---

## 1. User Management
Endpoints related to user exploration and validation.

### Get Users by Province
Retrieves a list of users filtered by their province name or province code. At least one query parameter must be provided.

- **URL:** `/api/users/by-province`
- **Method:** `GET`
- **Query Parameters:**
  - `provinceName` (String, optional)
  - `provinceCode` (String, optional)
- **Responses:**
  - **200 OK:** Successfully retrieved list of users.
  - **400 Bad Request:** If neither `provinceName` nor `provinceCode` are provided.

**Example Request:**
```http
GET /api/users/by-province?provinceName=Kigali
```

---

### Check Email Existence
Validates whether a user with the provided email address already exists in the system. 

- **URL:** `/api/users/check-email`
- **Method:** `GET`
- **Query Parameters:**
  - `email` (String, required)
- **Responses:**
  - **200 OK:** Returns a boolean indicating existence.

**Example Request:**
```http
GET /api/users/check-email?email=johndoe@example.com
```

**Example Response:**
```json
{
  "exists": true
}
```

---

## 2. Location Management
Endpoints related to geographic locations and provinces.

### Get All Provinces (Paginated & Sorted)
Fetches all available top-level locations (provinces) with support for pagination and sorting.

- **URL:** `/api/locations/provinces`
- **Method:** `GET`
- **Query Parameters:**
  - `page` (Integer, optional, default: 0): The zero-indexed page number.
  - `size` (Integer, optional, default: 10): The size of the page to return.
  - `sortBy` (String, optional, default: "name"): The entity property to sort by.
  - `dir` (String, optional, default: "asc"): Sorting direction ("asc" or "desc").
- **Responses:**
  - **200 OK:** Returns a paginated object (`Page<Location>`) containing the list of provinces.

**Example Request:**
```http
GET /api/locations/provinces?page=0&size=5&sortBy=name&dir=asc
```

---

### Get Child Locations (Paginated & Sorted)
Retrieves the sub-locations (like districts or sectors) belonging to a specific parent location.

- **URL:** `/api/locations/{parentId}/children`
- **Method:** `GET`
- **URL Parameters:**
  - `parentId` (Long, required): The ID of the parent location.
- **Query Parameters:**
  - `page` (Integer, optional, default: 0): The zero-indexed page number.
  - `size` (Integer, optional, default: 10): The size of the page to return.
  - `sortBy` (String, optional, default: "name"): The entity property to sort by.
  - `dir` (String, optional, default: "asc"): Sorting direction ("asc" or "desc").
- **Responses:**
  - **200 OK:** Returns a paginated object (`Page<Location>`).

**Example Request:**
```http
GET /api/locations/1/children?page=0&size=10
```

---

### Batch Upload Locations
Provides a bulk insertion endpoint to populate the database with extensive hierarchical Location datasets. Built to safely resolve `ManyToOne` parent relationships using the 8-character String `code`.

- **URL:** `/api/locations/batch`
- **Method:** `POST`
- **Body:** JSON Array of `Location` objects.
- **Responses:**
  - **200 OK:** Returns the saved `Location` objects reflecting database IDs.

**Example Request:**
```http
POST /api/locations/batch
[
  { "name": "Kigali City", "code": "1", "type": "PROVINCE" }
]
```

---

## 3. Property Management
Endpoints for browsing and adding rental properties.

### Get All Properties (Paginated)
Retrieves a paginated and sortable list of all rental properties.

- **URL:** `/api/properties`
- **Method:** `GET`
- **Query Parameters:**
  - `page` (Integer, optional, default: 0): The zero-indexed page number.
  - `size` (Integer, optional, default: 10): The size of the page to return.
  - `sortBy` (String, optional, default: "id"): The entity property to sort by.
  - `direction` (String, optional, default: "asc"): Sorting direction ("asc" or "desc").
- **Responses:**
  - **200 OK:** Returns a paginated object containing the list of properties.

**Example Request:**
```http
GET /api/properties?page=0&size=5&sortBy=price&direction=desc
```

---

### Create a New Property
Adds a new rental property to the system.

- **URL:** `/api/properties`
- **Method:** `POST`
- **Body:**
  Required JSON object representing the `Property` details, which must include references to existing `landlord` and `location` entities.
- **Responses:**
  - **201 Created:** The property was built and saved successfully. Returns the saved property object.
  - **400 Bad Request:** If validation fails on the provided JSON body.
  - **500 Internal Server Error:** If related entities (like the landlord or location) do not exist or violate database constraints.

**Example Request Body:**
```json
{
  "title": "Modern 2BHK Apartment",
  "description": "A fully furnished apartment in the center of Kigali.",
  "price": 500000.00,
  "bedrooms": 2,
  "bathrooms": 2,
  "area": 120.5,
  "landlord": {
    "id": 1
  },
  "location": {
    "id": 3
  }
}
```
---

## 4. Bookings Management
Endpoints for booking properties and managing rental status.

### Create a Booking (Rent a Property)
Allows a tenant to book a house for specific dates.

- **URL:** `/api/bookings`
- **Method:** `POST`
- **Query Parameters:**
  - `propertyId` (Long, required): The ID of the property to book.
  - `tenantId` (Long, required): The ID of the tenant booking.
  - `startDate` (String, required): ISO Date `YYYY-MM-DD`.
  - `endDate` (String, required): ISO Date `YYYY-MM-DD`.
- **Responses:**
  - **200 OK:** Successfully created the booking with `PENDING` status.
  - **400 Bad Request:** Missing parameters or invalid entities.

**Example Request:**
```http
POST /api/bookings?propertyId=1&tenantId=2&startDate=2026-06-01&endDate=2026-06-15
```

---

### Update Booking Status
Allows landlord or system to confirm or cancel bookings.

- **URL:** `/api/bookings/{id}/status`
- **Method:** `PUT`
- **Query Parameters:**
  - `status` (String, required): Values could be `CONFIRMED`, `PENDING`, `CANCELLED`.
- **Responses:**
  - **200 OK:** Successfully updated.
  - **400 Bad Request:** If booking is not found.

**Example Request:**
```http
PUT /api/bookings/1/status?status=CONFIRMED
```

---

### Fetch Bookings
You can retrieve bookings by ID, Tenant, or Property.

- **URL:** `/api/bookings/property/{propertyId}` or `/api/bookings/tenant/{tenantId}`
- **Method:** `GET`
- **Responses:**
  - **200 OK:** List of bookings.

---

## 5. Review Management
Endpoints for leaving reviews on properties tied to confirmed bookings.

### Add a Review
Allows a tenant to leave a rating and comment for a *confirmed* booking.

- **URL:** `/api/reviews`
- **Method:** `POST`
- **Query Parameters:**
  - `bookingId` (Long, required): The ID of the confirmed booking.
  - `rating` (Integer, required): Value from 1 to 5.
  - `comment` (String, required): Feedback content.
- **Responses:**
  - **200 OK:** Review added successfully.
  - **400 Bad Request:** If booking is not confirmed or already reviewed.

**Example Request:**
```http
POST /api/reviews?bookingId=1&rating=5&comment=Great%20experience!
```

---

## 6. Database Schema & Architecture
This project is mapped relationally adhering strictly to ORM specifications:

- **Entity Relationship Diagram (ERD)**: Consists of 6 tightly integrated tables `Users`, `Locations`, `Properties`, `Amenities`, `Property_Amenities`, and `Booking` (with `Review`).
- **ManyToMany**: `Property` and `Amenity` utilize an implicit join table (`property_amenities`) to manage multiple tags.
- **OneToMany**: `User` acts as a Landlord holding a `@OneToMany` mapping of `Property` records.
- **OneToOne**: `Booking` links to a singular `Review`. Handled utilizing `@OneToOne` with a `unique=true` column constraint to ensure referential safety.
- **Advanced JPQL**: `UserRepository` executes a `WITH RECURSIVE` geospatial JPQL querying structure to retrieve users hierarchically by checking their localized District/Village against the Parent Province Code dynamically.

# midterm_26757_group-B
