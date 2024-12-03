@apiTests1
Feature: Verify that the Group has enrolled or not

  Background:
    Given I have generated a bearer token for an Organisation

  Scenario: Verify that the Group has enrolled or not
    Given I make api call to plr uktr "<Stub>" for 201
      Then I verify response code is 201
