@apiTests
Feature: Verify UKTR submission apis

  Scenario Outline: verify error code 422
#    Given I make api call to uktr stub for <StatusCode> response code using <Stub>
    Given I make api call to uktr "<Stub>" for <StatusCode>
    Then I verify response code is <StatusCode>
    Examples:
      | StatusCode | Stub            |
      | 422        | XEPLR0000000422 |
      | 400        | XEPLR0000000400 |
      | 500        | XEPLR0000000500 |
      | 201        | XEPLR0000000111 |