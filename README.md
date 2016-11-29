# EaSQLite
EaSQLite is an extremely simple Android Local Database tool that is used to store information in an App even after that App is shut down. It uses the SQLite Android Wrappers and provides a simple interface with very little overhead to execute simple SQL tasks!

## Usage
Here is a [link](http://sirjankafle.me/easqlite/) to the Javadocs.

EaSQLite is very easy to use. There is a static class `EaSQLite` that contains all the methods you need, abstracting away all the details the SQLite wrapper native to Android contains such as Cursors.

### Startup
In order to first initialize the database, in the `onCreate` of your `MainActivity`, simply add a call `EaSQLite.initialize(getApplicationContext())`. For instance, in the repository there is a Demo App that contains three tables in the database: nfl, nba, and nhl. Each table has columns describing attributes of several players in the respective leagues such as name, age, height, and team. In the `onCreate` of our `IntroActivity` we have:
```java
try {
    EaSQLite.initialize(getApplicationContext());
} catch (InvalidTypeException|InvalidInputException|IOException|ClassNotFoundException e) {
    new AlertDialog.Builder(IntroActivity.this)
        .setTitle("Database Corrupted")
        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 finish();
             }
         }).show();
}
```
The various Exceptions should be caught and handled notifying the user that the Database contains invalid information (this occurs if certain Integrity Constraints are invalid for some reason). This commonly occurs when the developer updates the database, but the app contains existing information, in which case the data should be cleaned.

You can also get all the table names using: `EaSQLite.getTableNames()`, and get column names using: `EaSQLite.getColumnNames(tableName)`. There are many other useful methods in the Javadoc. To return back to the Demo App, if we wanted to display all the information in a certain table, we can simply grab a certain column and put it in a ListView:
```java
List<Object> playerNames = EaSQLite.getColumn(tableName, "name");
List<Object> playerAges = EaSQLite.getColumn(tableName, "age");
List<Object> playerHeights = EaSQLite.getColumn(tableName, "height");
List<Object> playerTeams = EaSQLite.getColumn(tableName, "team");
```

Finally to actually create and add entries, you can use the `addRow` method as such:
```java
String name = nameField.getText().toString();
int age = Integer.parseInt(ageField.getText().toString());
String height = heightField.getText().toString();
String team = teamField.getText().toString();
Object[] entries = {name, age, height, team};

List<String> colNames = EaSQLite.getColumnNames(league);
Pair<String, Object>[] row = new Pair[colNames.size()];

int i = 0;
for (String column : colNames) {
    row[i] = new Pair<String, Object>(column, entries[i]);
    i++;
}

try {
    EaSQLite.addRow(league, row);
} catch (IOException e) {
    Toast.makeText(AddActivity.this, "Addition to DB failed", Toast.LENGTH_SHORT).show();
}
```
Note that `addRow` throws an Exception if the addition is invalid. Thus this needs to be caught and the user must be notified accordingly. Also note for row creation, an array of Pairs is passed in to specify the column name and row object. This way, order does not have to be preserved.

Because of the simple static class EaSQLite, very few lines are needed to undergo simple tasks in a database. One big thing to note is that the user should be notified when EaSQLite throws an Exception because it is most likely because some Integrity Constraint is violated or because of invalid names/types.

## Requirements
Android 2.2+

## License
MIT License

Copyright (c) [2016] [Mobile Developers of Berkeley]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
