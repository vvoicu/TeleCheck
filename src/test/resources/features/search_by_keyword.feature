Feature: Searching by keyword

  In order to find items that I would like to purchase
  As a potential buyer
  I want to be able to search for items containing certain words

  Scenario: Should list items related to a specified keyword
    Given I want to buy 'wool'
    When I search for items containing 'wool'
    Then I should only see items related to 'Anything with a texture like that of wool'
    
    
  Scenario: Negative - Should list items related to a specified keyword
    Given I want to buy 'wool'
    When I search for items containing 'wool'
    Then I should only see items related to 'that of wool'