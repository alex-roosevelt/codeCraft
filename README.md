
### Test Data for Saving ZIP

To test the ZIP file generation, use the following `curl` command to send a request to the API. This will generate a ZIP file containing the specified project data.

#### Request:

```bash
curl --location 'http://localhost:8080/api/v1/projects/generate-zip' \
--header 'Content-Type: application/json' \
--data '{
    "packageName": "com.test.example",
    "appName": "App",
    "artifactId": "app",
    "entities": [
        {
            "name": "Product",
            "fields": "id (Long), name (String), price (BigDecimal)"
        },
        {
            "name": "Category",
            "fields": "id (Long), title (String), description (String)"
        }
    ]
}'

Explanation:
packageName: The base package name for the generated project.
appName: The name of the application.
artifactId: The artifact ID for the project.
entities: A list of entities to be included in the generated project. Each entity includes:
name: The name of the entity (e.g., Product, Category).
fields: The fields for the entity, specified by type and name (e.g., id (Long), name (String), price (BigDecimal)).
When you run this request, a ZIP file will be generated containing the project files based on the provided entity definitions.
