# Automation of Orders placement in Kraken exchange for Crypto currencies trading

## Idea 
The fundamental idea of this personal project is to benefit from the volatility of crypto currencies when they are trading within a particular range. For instance, in 2022 and 2023, the price of Bitcoin stayed within the range of 20,000 and 40,000 USD.

Using this project, I bought and sold BTC automatically over those 2 years at particular price intervals that I defined.

Eg: I have placed the below "take-profit-limit" orders as a one-off activity in Kraken exchange,

To buy 100 dollars worth BTC at price 20000 dollars and sell it at a profit of 5.5% i.e, 20000*1.055 = 21100 dollars
To buy 100 dollars worth BTC at price 21000 dollars and sell it at a profit of 5.5% i.e, 21000*1.055 = 22155 dollars
To buy 100 dollars worth BTC at price 22000 dollars and sell it at a profit of 5.5% i.e, 22000*1.055 = 23210 dollars
To buy 100 dollars worth BTC at price 23000 dollars and sell it at a profit of 5.5% i.e, 23000*1.055 = 24265 dollars
.....
.....
.....
To buy 100 dollars worth BTC at price 39000 dollars and sell it at a profit of 5.5% i.e, 39000*1.055 = 41145 dollars
To buy 100 dollars worth BTC at price 40000 dollars and sell it at a profit of 5.5% i.e, 40000*1.055 = 42200 dollars

This project helped maintain those orders all the time over those 2 years without manual intervention. Meaning, the moment one of those orders get executed, this project will recreate another order for the same buy and sell values and put it in place.

So, with this strategy and automation, essentially the more volatile Bitcoin is within the defined price range, the more number of trades get executed and more profit can be made. Converse is also true.

With the above strategy, obviously, if the price of Bitcoin goes below 20000 dollars indefinitely, the money used for the executed buy orders (that did not get sold) will be lost.

The strategy can be modified/customized by making changes to the KrakenAutomatedOrders.java file.

## Technical details
This project is coded in Java and is meant to be built as a jar file and put in AWS lambda to constantly monitor the state of orders in the Kraken account. It's not very efficient to constantly poll the orders, however, I couldn't find a way in Kraken(like hooks) to trigger the lambda after an order gets implemented. Since, this is a personal project, I could make use of the free tier of AWS to run the lambda for free throughout the year.
(Note that, I coded this project hastily to create a working version so that I can personally use it for automated order creation, hence did not bother about best practices, performance and automated-testing, leaving a huge scope for improvement)

The needed code to call Kraken API is copied from an initial snapshot version of https://github.com/nyg/kraken-api-java. That project has evolved quite a bit over the years though.

## How to build and use
Key and Secret of your Kraken account needs to be set in KrakenAutomatedOrders.java file.

This project uses Maven as the build tool.

Run the below command to build the shaded jar(*-with-dependencies.jar) needed for uploading in AWS Lambda.

mvn clean package shade:shade

Make sure to set up the AWS Lambda environment variable "PROFIT_PERCENTAGE" and also set up a scheduler to trigger the Lambda at a regular interval of your choice. 