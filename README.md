# Opening Hours API

By Atte Lassila

## Description

The project listens to POST requests on `localhost:8080/format`. Results of the formatting are returned as plain text
in the HTTP response. 

## Requirements

Java 11

## How to run

The project can be started using `gradlew` or `gradlew.bat`, depending on your system.

On MacOS/Linux:

```
./gradlew bootRun
``` 

On Windows:

```
gradlew.bat bootRun
```

## Thoughts on API

> Is the current JSON structure the best way to store that kind of data or can you come up with a better version?

The shape of the data is usually influenced by how the data is used. Could be that for some use case, containing
opening hours in a form such as this, with them listed as opening/closing events grouped by day makes perfect sense. 
So containing the data in a form such as this is not a problem **if** it means that whatever software is using the data 
has an easier time processing it. 

One objective improvement could be made however: renaming `value` to the more descriptive `secondOfDay`, to make it easier
to understand what the field contains. 

If I were to design a JSON structure that would be easier to handle in this particular case, it might look something like
this:

```
[
  {
    "type": "open",
    "day": "MON",
    "secondOfDay": 3600
  },
  {
    "type": "close",
    "day": "MON",
    "secondOfDay": 36000
  },
  {
    "type": "open",
    "day": "TUE",
    "secondOfDay": 36000
  },
  {
    "type": "close",
    "day": "TUE",
    "secondOfDay": 64000
  },
]
```
