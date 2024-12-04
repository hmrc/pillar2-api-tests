@apiTests
Feature: Verify UKTR submission apis

  Scenario Outline: verify error codes for uktr submission
    Given I make api call to uktr "<Stub>" for <StatusCode>
    When I verify response code is <StatusCode>
    Then I validate json schema for "<JsonSchema>"
    Examples:
      | StatusCode | Stub            | JsonSchema                          |
      | 422        | XEPLR0000000422 | jsonSchema/uktrSchema/UKTR_422.json |
      | 400        | XEPLR0000000400 | jsonSchema/uktrSchema/UKTR_400.json |
      | 500        | XEPLR0000000500 | jsonSchema/uktrSchema/UKTR_500.json |
      | 201        | XEPLR0000000111 | jsonSchema/uktrSchema/UKTR_201.json |