test data for save zip
curl --location 'http://localhost:8080/api/v1/projects/generate-zip' \
--header 'Content-Type: application/json' \
--data '{
    "packageName": "com.veon.eurasia",
    "appName": "App",
    "artifactId":"app",
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
