# Toll Roads Blockchain Management System 

## 1. PROBLEMS

Toll roads payment infrastructure (from booths to the Regulator) face a series of logistical problems, which can be successfully addressed by Blockchain.

### 1.1. Safety
Among many concerns involving safety in Toll Roads and their contexts, two very serious are:

#### 1.1.1. Robbery
When the tolls are collected in cash by the booths, a great amount of money hangs in there to be stolen. In Brazil, for instance, it is not a rare fact to have toll booths robbed in the late night. The armored cars that take the money from the booths to the bank are also at risk of being robbed. Putting the funds in the Blockchain entirely solves this problem.

#### 1.1.2. Lack of Transparency (corruption)
Regulators are usually part of the public sector, maintained by taxpayers' money. When that is the case, the only data sources regarding the number of vehicles that have paid are the Operators and the Regulators themselves, being that the later has to rely solely on the former for those data. In this context, when money is detoured from the Regulator, it is public money that is being stolen. By using Blockchain, not only the number of paying vehicles but also the total amount collected by the Operator and due to the Regulator are transparently visible to anyone.


### 1.2. Speed
Toll Roads systems also face issues related to speed in both, funds transfer and implementation of changes.

#### 1.2.1. Funds transfer
In the current state of affairs, the cash is collected by the booths which are periodically visited by armored cars which take the funds to the bank. After that, the Operator needs to send the Regulator's collected fee (when applicable). By using Blockchain, both, Operators and Regulators can receive the funds as soon as they are transferred to the booth. 

#### 1.2.2. Changing Rules
Rules, such as pricing, may change. When that happens, a logistical effort must be made to ensure that all booths are updated. Another thing that can change is the fee charged by the Regulator, which requires a lot of communication between the parties. If the rules, on the other hand, are implemented on the Blockchain, every change can take place seamlessly.

#### 1.2.3. Changing Road Operation Grants
A toll road may have its Operator changed. In these situations, a change in the employees' contracts or in the infrastructure of the booths may also take place. On the financial side, however, if the system is implemented on Blockchain, the only thing that needs to be changed is the owner of the Operator contract (the Blockchain one). After that, the funds can again be seamlessly sent from the booths to the correct Operator and Regulator without further changes.


### 1.3. Price
Another factor that might impact the toll value is the cost of transactions. Operators have high expenses with credit card operators and money transfers from bank to bank (e.g. to pay the Regulator), which can be substantially decreased by adopting a Blockchain as the money management platform.



## 2. PRICING

There are three questions regarding pricing: **the chargeable unit**, **the markup**, and **the annual increase**.

### 2.1. The Chargeable Unit
Different vehicles put different strains on the road. If we consider a road as a resource, a motorcycle consumes way less resources than a truck. An acceptable correlation between the strain and the vehicle is the number of wheels. That way, a regular four-wheels vehicle will pay more than a motorcycle but less than a truck, and larger trucks will be charged more than smaller ones.

### 2.2. The Markup (profit margin)
In a free market society, prices - and profit margins - are regulated by supply/demand. However, in monopolistic services, such as a toll road (even in a consortium, a booth section in a given road does not usually face competition), external regulation must take place, in order to prevent abusive practices. 

To calculate an acceptable price, the Regulator must take into account: *the initial investment made by the operator* (booths construction, roads improvement, softwares, etc), *the operational costs* (salaries, infrastructure maintenance, transaction costs [in gas or not], etc), *return over investment (ROI) horizon*, and the *average number of wheels expected in a given time frame*. In addition, those costs are usually paid in local currency, thus, to convert them to Ether, an exchange rate calculation should be performed. An **Oracle** could be useful in this situation.

For the sake of the example, let's assume:

*Local currency* = USD
*USD/ETH* = $300,00 (**from Oracle**)
*Initial investment* = $1 billion
*ROI horizon* = 5 years
*Markup* = 25%
*Monthly operational costs per toll station* = $1 million
*Average number of regular 4-wheel vehicles per toll station* = 1 million

Annualized estimated price in USD = ((Initial Investment / ROI) + monthly costs x 12) / (Average number of vehicles x 12) x Markup
Annualized estimated price in USD = (($1 billion / 5) + $1 million) / (1 million x 4) * 1.25
*Annualized estimated price in USD* = **$22,0**

*Annualized price **per wheel** in USD* = $22,0 / 4 = **$5.5**

*Annualized estimated price per wheel in ETH* = $5.5/$300 = **183 finney + gas**


###2.3. Annual Increase
If the markup is good enough, the only price increase acceptable is the *inflation rate*. In this scenario, keeping the markup constant would encourage Operators to improve their processes in order to improve efficiency (possibly good to customers), thus, decreasing costs, thus, increasing their margins.



## 3. CONTRACTS
Five different types of contract can exist in this application. Their main responsibilities are below.

### 3.1. The Regulator
. Deploys and controls Operators' contracts.
. Receives funds paid as fees from Operators.
. Transfers Operator's contracts ownership.
. Defines the pricing criteria: price per wheel, markup and annual increase.

### 3.2. The Road Operator
. Deploys and controls Toll Booths contracts.
. Receives funds from Toll Booths.
. Transfers funds to Regulators according to the specified fee.

### 3.3. The Toll Booth
. Receives fees from Drivers and Vehicles and forwards them to the Operator.
. Has a refund function for the narrow case where the Driver has issued a transaction but the network went down and the Driver has payed with money.

### 3.4. The Driver
. The Driver is not a contract but a regular Ethereum account.
. The Driver pays to the Toll Both based on the number of wheels of the car he is driving when going through the road.

### 3.5. The Vehicle 
. The Vehicle can be a contract for situations where it does not belong to the driver. For instance, if the vehicle belongs to a company and is used by multiple employees on duty, instead of having the employees to spend their own funds and then be reimbursed, the vehicle can have its own funds.


## 4. INTERACTIONS
Regarding the interactions of the contracts:

. The Regulator and its Operators are implemented according to the Hub/Spokes pattern, as well as the Operator and its Toll Booths and also the Regulators themselves, which are created by a Hub.
. The toll paid by Drivers and Vehicles are immediately transferred to the Operator and Regulator (if applicable).
. Operators can kill Toll Booths as well as Regulators can kill Operators.
. Operators may work as Oracles, allowing users to know in advance how much will be paid in a given Toll Booth.


