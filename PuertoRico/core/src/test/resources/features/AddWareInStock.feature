Feature: Add ware in stock

  Background:
    Given a player of name "Joueur0" with the character trader and the stock of the game
    And a building "SmallIndigoPlant"

  Scenario: ware is added in sell chamber stock
    When "Joueur0" have 1 "INDIGO"
    And "Joueur0" choose to play the trader
    Then "Joueur0" gain 1 doublon
    And 1 "INDIGO" is added in sell chamber stock

  Scenario: ware is not added in stock
    When "Joueur0" have 0 "INDIGO"
    And "Joueur0" choose to play the trader
    Then "Joueur0" gain 0 doublon
    And 0 "INDIGO" is added in sell chamber stock
