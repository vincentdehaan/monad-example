# monad-example

```
sbt run

curl http://localhost:8080/resume \
  -X POST \
  -d '{"tpe": "FRONTEND", "educationLevel": 2 }' \
  -H "Content-Type: application/json" \
  -H "X-TraceId: 123" | jq

```