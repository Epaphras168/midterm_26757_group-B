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

### Get All Provinces
Fetches all available top-level locations (provinces).

- **URL:** `/api/locations/provinces`
- **Method:** `GET`
- **Responses:**
  - **200 OK:** Returns an array of province objects.

**Example Request:**
```http
GET /api/locations/provinces
```

---

### Get Child Locations
Retrieves the sub-locations (like districts or sectors) belonging to a specific parent location (province).

- **URL:** `/api/locations/{parentId}/children`
- **Method:** `GET`
- **URL Parameters:**
  - `parentId` (Long, required): The ID of the parent location.
- **Responses:**
  - **200 OK:** Returns an array of location objects.

**Example Request:**
```http
GET /api/locations/1/children
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
# midterm_26757_group-B
