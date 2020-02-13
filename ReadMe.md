# Descriptions

**Build the project:**
Run the following command with pom.xml, a searchService.war file will be created. This application was run/tested on my mac machine only at this moment.
```
mvn clean install -DskipTests
```
**Run the project:**
Deploy the war file to Tomcat server and start tomcat, you should be able to access: http://localhost:8080/searchService/

**Sample API request:**

{
	"keyword": "coca-cola"
}

**Sample API response:**

{
    "statusCode": "SUCCESS",
    "statusDetails": [
        {
            "statusCode": "SUCCESS",
            "source": "iTunes",
            "timeComsumed": "0.707 seconds",
            "record": 5
        },
        {
            "statusCode": "SUCCESS",
            "source": "Google Book",
            "timeComsumed": "1.185 seconds",
            "record": 5
        }
    ],
    "results": [
        {
            "type": "book",
            "creaters": [
                "Alexandra Chreiteh"
            ],
            "title": "Always Coca-Cola"
        },
        {
            "type": "album",
            "creaters": [
                "Kenny Burrell"
            ],
            "title": "Be Yourself (Live at Dizzy's Club Coca-Cola)"
        },
        {
            "type": "book",
            "creaters": [
                "Axel Schildt",
                "Detlef Siegfried"
            ],
            "title": "Between Marx and Coca-Cola"
        },
        {
            "type": "book",
            "creaters": [
                "Mark Pendergrast"
            ],
            "title": "For God, Country, and Coca-Cola"
        },
        {
            "type": "album",
            "creaters": [
                "Monty Alexander"
            ],
            "title": "Harlem-Kingston Express (Live at Dizzy's Club Coca-Cola, NYC)"
        },
        {
            "type": "book",
            "creaters": [
                "Neville Isdell",
                "David Beasley"
            ],
            "title": "Inside Coca-Cola"
        },
        {
            "type": "album",
            "creaters": [
                "Ernestine Anderson"
            ],
            "title": "Nightlife (Live at Dizzy's Club Coca-Cola)"
        },
        {
            "type": "album",
            "creaters": [
                "Eric Reed & Cyrus Chestnut"
            ],
            "title": "Plenty Swing, Plenty Soul (Recorded Live at Dizzy's Club Coca-Cola)"
        },
        {
            "type": "album",
            "creaters": [
                "Freddy Cole"
            ],
            "title": "The Dreamer in Me: Jazz at Lincoln Center (Live at Dizzy's Club Coca-Cola)"
        },
        {
            "type": "book",
            "creaters": [
                "Valerie Bodden"
            ],
            "title": "The Story of Coca-Cola"
        }
    ]
}
