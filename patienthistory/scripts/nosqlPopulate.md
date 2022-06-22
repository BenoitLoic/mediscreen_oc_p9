### Start DB

```
    mongod --dbpath=data/db
```

### Create Collection

```
   use mediscreen_mongo; 
   db.createCollection("history");
```

### Populate "history" Collection.

```
db.history.insert([
{ "_id" : "00000000000000000000001", "patientId" : NumberInt(1), "familyName" : "lastNameTest1", "givenName" : "firstNameTest1", "notes" : [ { "date" : "2022-05-20T20:44:41", "text" : "premier text" }, { "date" : "2022-06-20T20:44:41", "text" : "deuxieme text" }, { "date" : "2022-06-20T20:44:41", "text" : "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam est tortor, pharetra in augue at, gravida vestibulum tellus. Suspendisse sed dictum enim. Proin vulputate ante non ipsum rhoncus, luctus laoreet lacus tincidunt. Duis rutrum ullamcorper est, non aliquet leo vehicula vitae. Nullam orci odio, iaculis ut mi in, hendrerit commodo lacus. Nunc iaculis tellus sit amet imperdiet volutpat. In vehicula dolor turpis, et tincidunt diam sagittis eget. Aenean rhoncus orci finibus ultricies rhoncus. Maecenas fermentum ultrices placerat. Mauris lacinia commodo tellus, eu placerat sem congue in. In a lorem iaculis sem cursus bibendum. Donec tellus mi, iaculis id felis sit amet, ullamcorper bibendum velit. Nunc fermentum rutrum commodo. Pellentesque tincidunt vel mi id efficitur. Nulla facilisi.Sed at tempus sem. Proin nec dolor ante. Maecenas cursus leo vitae erat tincidunt efficitur. Quisque suscipit scelerisque magna vitae ullamcorper. Nullam ac velit commodo, faucibus ipsum vitae, consectetur arcu. Sed quam lacus, fringilla non massa vitae, mollis congue tortor. Nullam mollis lacinia augue ut gravida. Nulla dignissim lectus massa, sit amet pulvinar mauris suscipit non. Proin ac fermentum augue. Suspendisse eu hendrerit lectus, ac dapibus nulla. In pharetra purus id leo dapibus vehicula. In hac habitasse platea dictumst. Sed faucibus libero ut efficitur rutrum. Vestibulum sed metus ultricies, euismod urna ac, efficitur est. Etiam elementum velit faucibus justo aliquam eleifend. " } ] },
{ "_id" : "00000000000000000000002", "patientId" : NumberInt(2), "familyName" : "lastNameTest2", "givenName" : "firstNameTest2", "notes" : [ { "date" : "2022-05-20T20:44:41", "text" : "premier text" }, { "date" : "2022-06-20T20:44:41", "text" : "deuxieme text" }, { "date" : "2020-06-20T20:44:41", "text" : "deuxieme text" } ] },
{ "_id" : "00000000000000000000003", "patientId" : NumberInt(3), "familyName" : "lastNameTest3", "givenName" : "firstNameTest3", "notes" : [ { "date" : "2022-05-20T20:44:41", "text" : "premier text" }, { "date" : "2022-06-20T20:44:41", "text" : "deuxieme text" }, { "date" : "2024-06-20T20:44:41", "text" : "deuxieme text" } ] },
{ "_id" : "00000000000000000000004", "patientId" : NumberInt(4), "familyName" : "lastNameTest4", "givenName" : "firstNameTest4", "notes" : [ { "date" : "2022-05-20T20:44:41", "text" : "premier text" }, { "date" : "2022-06-20T20:44:41", "text" : "deuxieme text" }, { "date" : "2019-06-20T20:44:41", "text" : "deuxieme text" } ] }
]);
```