@apiTests
Feature: Verify UKTR submission apis

  Scenario Outline: verify error codes for uktr submission
    Given I make api call to uktr "<Stub>" for <StatusCode>
    Then I validate request json schema for "jsonSchema/uktrSchema/Requests/SubmitUKTR_Request.json"
    When I verify response code is <StatusCode>
    Then I validate response json schema for "<JsonSchema>"
    Examples:
      | StatusCode | Stub            | JsonSchema                                   |
      | 422        | XEPLR0000000422 | jsonSchema/uktrSchema/Response/UKTR_422.json |
      | 400        | XEPLR0000000400 | jsonSchema/uktrSchema/Response/UKTR_400.json |
      | 500        | XEPLR0000000500 | jsonSchema/uktrSchema/Response/UKTR_500.json |
      | 201        | XEPLR0000000111 | jsonSchema/uktrSchema/Response/UKTR_201.json |