Given {employeeID: String}
When PUT to /parkingboys
Then it should return 201.

Given empty content
When GET to /parkingboys
Then it should return 200. and list of employeeID in content

