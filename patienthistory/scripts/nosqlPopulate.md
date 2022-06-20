### Create Collection

```
   use mediscreen_mongo; 
   db.createCollection("history");
```

### Populate "history" Collection.

```
db.history.insert([
{ "_id" : "00000000000000000000001", "patientId" : NumberInt(1), "familyName" : "lastNameTest1", "givenName" : "firstNameTest1", "notes" : [ { "date" : "2022-05-20T20:44:41", "text" : "premier text" }, { "date" : "2022-06-20T20:44:41", "text" : "deuxieme text" } ] },
{ "_id" : "00000000000000000000002", "patientId" : NumberInt(2), "familyName" : "lastNameTest2", "givenName" : "firstNameTest2", "notes" : [ { "date" : "2022-05-20T20:44:41", "text" : "premier text" }, { "date" : "2022-06-20T20:44:41", "text" : "deuxieme text" } ] },
{ "_id" : "00000000000000000000003", "patientId" : NumberInt(3), "familyName" : "lastNameTest3", "givenName" : "firstNameTest3", "notes" : [ { "date" : "2022-05-20T20:44:41", "text" : "premier text" }, { "date" : "2022-06-20T20:44:41", "text" : "deuxieme text" } ] },
{ "_id" : "00000000000000000000004", "patientId" : NumberInt(4), "familyName" : "lastNameTest4", "givenName" : "firstNameTest4", "notes" : [ { "date" : "2022-05-20T20:44:41", "text" : "premier text" }, { "date" : "2022-06-20T20:44:41", "text" : "deuxieme text" } ] }
]);
```