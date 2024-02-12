Feature: Add a new pet to the store

#  Background: Can be provided if there is a precondition for the test

  @AddPet
  Scenario Outline:As a Store Owner, I would like to add new pet profile.
    Given I have new pet to add to the store
    When I pass the url of add pet in the request
    And I pass the request body of add new pet <id> <name> <categoryName> <photoUrls> <tagName> <status>
    Then I receive the response code as 200
    Then I receive the response body with id as <id>
    Examples:
      | id    | name   | categoryName   | photoUrls  | tagName  | status    |
      | 744299| Lolly  | https://image1 | testUrls   | Adorable | available |
#    And  I perform a DELETE operation with ID and verify the data is removed.
#      |  Id     |
#      |  744299 |

  @AddPet-negative-scenario
  Scenario Outline:As a Store Owner, I dont want to add new pet profile with invalid data.
    Given I have new pet to add to the store
    When I pass the url of add pet in the request
    And I pass invalid request body <id> <name> <categoryName> <photoUrls> <tagName> <status>
    Then I receive the response code as 400
    Examples:
      | id    | name   | categoryName   | photoUrls  | tagName  | status    |
      | abc   | Lolly  | https://image1 | testUrls   | Adorable | available |



