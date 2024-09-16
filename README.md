# finet Capstone Proposal

## 1. Problem Statement

In the 21st century, there is a wealth of data pertaining to financial transactions, stocks, and their trends. It is no longer a matter of collecting data, so much as it is of connecting data. (Seth Godin @ https://seths.blog/2014/04/connecting-dots-or-collecting-dots/)

From the perspective of a recreational finance or stock enthusiast, it is hard to contend on a level playing field with the large financial firms that dominate today's markets. While large amounts of data can be accessed, many do not know how to turn it into actionable information that can be used to profit from trading.

## 2. Technical Solution

One solution to the problem of "data exhaustion" is to view the data in a novel way. A network is a system of data that are connected based on relationships they have with one another. Often times in the real world, "scale-free" networks emerge. Scale-free networks have a distribution of nodes where very few nodes contain a very high amount of connections, while most nodes contain a relatively low amount of connections. This unique statistical property embues scale-free networks with important qualities that are relevant to our lives, such as robustness to random failures, vulnerability to targeted attacks, similar appearance with varying resolution, and the "small world" effect, meaning that any location in the network can be reached via a relatively short path.

Many modern financial network phenomena have been observed to be "scale-free". Because of this, one's financial analysis could potentially benefit by the visualization of financial entities and trends in the form of a network, or mathematically, a graph, to ascertain unique characteristics that would not be evident given other forms of analysis.

This application will serve as a proof-of-concept for transforming financial data accessed through the Alpha Vantage Web API to a network-based representation in the Neo4j graph database. Entities and relationships will initially be loaded into and visualized by the database manually through the UI to provide reference points from which more intensive, automated analysis can be done in future endeavors.

### Scenario 1:
Francis wants to use the Alpha Vantage API to identify stocks with a good chance of outperforming the market. He uses the finet UI to search for a stock, view summary data from the API, and then load the stock into the Neo4j database. The stock is represented in the database as a node entity, and Jim can enter pre-made tags in the UI such as "watch", "good sentiment", "bad sentiment", "trending up", "trending down", "technology", "energy", "automotive", etc. that will be used in the database to form relationships, referred to in graph theory as edges, between stock entities. For instance, if Jim views Stock 1 and saves it to the database with the "good sentiment" and "technology" tags, Stock 1 - "good sentiment" and Stock 1 - "technology" relationships will be created.

### Scenario 2:
Mel has the same goals as Francis regarding analyzing stock performance, but also wants to establish direct relationships between various stocks to persist in the database. He can use the finet UI to search for a stock and then establish a relationship between an existing stock in the database by adding the name of the existing stock as a tag before saving.

### Scenario 3:
Noam wants to visualize the stock networks that have been created by Francis and/or Mel. He can navigate to the visualization page in the application, where the graphical data is served from the Neo4j database and visualized with a React library.

## 3. Glossary

Neo4j
@NodeEntity: Neo4j class responsible for holding entities as nodes in the database. These will be stocks.
@Relationship: Neo4j class reponsible for creating relationships between node entities, which can be thought of as edges in the graph.

Relationship tags
"watch": A stock is being watched
"good sentiment": There is good market sentiment on a stock
"bad sentiment": There is bad market sentiment on a stock
"trending up": The stock value has been increasing
"trending down": The stock value has been decreasing
"technology": Information technology sector stock
"automotive": Automotive sector stock
"energy": Energy sector stock
"finance": Finance sector stock
"health": Healthcare sector stock
"defense": Defense sector stock

## 4. High Level Requirement

Search for a stock to retrieve summary stock data (MEMBER).
Save the stock as a node entity in the database and establish relationships between the stock and one or more of the above-specified pre-made tags (MEMBER).
Save the stock as a node entity in the database along with one or more of any previously saved stocks in the database to establish relationships between the stocks (Member).
Visualize the stock network data on a page within the UI (Member).

## 5. User Stories/Scenarios

### Story 1:
As a user, I want to search for a stock to see its summary data. I want this summary data to include prices over the past month.

Suggested data:
Text search for stock symbol (input by user)
JSON sourced from Alpha Vantage Monthly Core Stock API

Precondition:
User must be on main page.

Postcondition:
The monthly stock data is displayed in chart form on the UI with a React library.

### Story 2:
As a user, after I successfully search for a stock and view its summary data, I want to have the choice to persist it in the graph database by associating it to a node entity and creating a relationship between the stock and one or more relevant tags. The relationships are represented as edges between the stock node and any selected tag nodes. I select tags to use as the other nodes in the relationships by using the relationship input feature in the UI. Pre-made tags will automatically appear when entering text in this feature and can be selected using a drop-drown menu. After they are selected, I use a button to finalize the action and persist the stock entities and relationships in the database.

Suggested data:
Previously selected stock symbol to use for creation of the stock node entity
Selected tags to use to create tag node entities if not already in existence and establish relationships between stock and tags

Precondition:
User must have already searched for a stock via the Alpha Vantage API so as to be ready to establish relationships between the stock and tags. Since tags are also represented by node entities, if a node entity has not already been created for a specific tag before the transaction, a node entity will be persisted to the database representing the tag during the transaction so that the relationship can be properly established. The relationship input feature will not be visible before a stock is queried.

Postcondition:
A message will be displayed notifying the user that the stock was persisted as a node entity in the database and listing the relationships that were established as edges between stock and tag nodes.

### Story 3:
As a user, after I successfully search for a stock and view its summary data, I may also want to create direct relationships between the stock and one or more existing stocks in the database. These relationships are represented as edges between the stock node and any selected existing stock. I select stocks to use as the other nodes in the relationships by using the same relationship input feature in the UI as in the case of the previous story. Existing stocks will automatically appear when entering text in this feature and can be selected using the drop-down menu. The new stock entities and their relationships to other stocks are persisted in the database using the same button from the previous story.

Suggested data:
Previously selected stock symbol to use for creation of the stock node entity
Previously existing stocks to use to establish inter-stock relationships

Precondition:
User must have already searched for a stock via the Alpha Vantage API and other stocks must already exist in the database so that relationships between them can be formed. The relationship input feature will not be visible before a stock is queried.

Postcondition:
A message will be displayed to notify the user that the stock was persisted as a node entity in the database and list the relationships that were established as edges between stock nodes.

### Story 4:
As a user, I would like to visualize the stock nodes and their tag and stock relationships as a holistic network in the UI itself, without having to rely on Neo4j's graph visualization tooling that would be external to the finet application. To see this visualization, I would navigate to the "/visualize" route by using a button on the main page. I can return to the main page using a corresponding button on the visualization page.

Suggested data:
Previously created stock and tag node entities
Previously established stock-tag and stock-stock relationships

Precondition:
None, but stock and tag node entities must exist in the database for anything to be displayed on the visualization page. If nothing is found yet in the database, a message will be displayed in the view notifying the user that no data is currently available.

Postcondition:
The data is successfully transferred to the React library responsible for creating the network visualization.