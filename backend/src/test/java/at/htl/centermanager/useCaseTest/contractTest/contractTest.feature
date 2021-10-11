Feature: Contract endpoint. An user of the endpoint is able to manage Contracts

  Background:
    * url baseUrl

  Scenario: Create a Contract
    Given path 'contracts'
    And request read('contract-test-create.json')
    When method POST
    Then status 200
    And match response == 'Contract for 5 added!'

  Scenario: Create a Contract again
    Given path 'contracts'
    And request read('contract-test-create.json')
    When method POST
    Then status 200
    And match response == 'Contract couldn\'t be added!'

  Scenario: Terminate a Contract
    Given path 'contracts/2'
    When method DELETE
    Then status 200
    And match response == 'Contract for 2 deleted!'

  Scenario: See how many shops a company has
    Given path 'contracts/byCompany/mediamarkt'
    When method GET
    Then status 202
    And match response == read('contracts-response-company.json')

  Scenario: See total rent for Company
    Given path 'contracts/totalRent/mediamarkt'
    When method GET
    Then status 202
    And match response == 'Total Rent for mediamarkt: 80201.0'

  Scenario: See total space for Company
    Given path 'contracts/totalSpace/mediamarkt'
    When method GET
    Then status 202
    And match response == 'Total Space for mediamarkt: 924.8 m2'

  Scenario: Extend Contract
    Given path 'contracts/1'
    And request read('contract-test-extend.json')
    When method PUT
    Then status 200
    And match response == 'Contract for 1 updated!'

  Scenario: Change shop to already used
    Given path 'contracts/1'
    And request read('contract-test-changeShop.json')
    When method PUT
    Then status 200
    And match response == 'Contract for 1 couldn\'t be updated!'