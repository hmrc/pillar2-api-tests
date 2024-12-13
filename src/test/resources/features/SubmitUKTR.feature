@apiTests @uktr
Feature: Verify UKTR submission apis

  Scenario Outline: verify status codes and schema for uktr submission
    Given I make API call to UKTR with "<PLRID>"
    Then I validate request json schema for "jsonSchema/uktrSchema/Requests/SubmitUKTR_Request.json"
    When I verify response code is <StatusCode>
    Then I validate response json schema for "<JsonSchema>"
    Examples:
      | StatusCode | PLRID           | JsonSchema                                   |
      | 422        | XEPLR0000000422 | jsonSchema/uktrSchema/Response/UKTR_422.json |
      | 400        | XEPLR0000000400 | jsonSchema/uktrSchema/Response/UKTR_400.json |
      | 500        | XEPLR0000000500 | jsonSchema/uktrSchema/Response/UKTR_500.json |
      | 201        | XEPLR0000000111 | jsonSchema/uktrSchema/Response/UKTR_201.json |

  @test
  Scenario Outline: Error messages validation for UKTR external stubs
    Given I make API call to UKTR with PLRID "XEPLR0000000421" , "<idType>", "<idValue>", "<amountOwedDTT>"
    When I verify response code is 422
    Then I verify error code is "003" and error message is "<ErrorMessage>"
    And I validate response json schema for "jsonSchema/uktrSchema/Response/UKTR_idType.json"
    Examples:
      | idType | idValue  | amountOwedDTT | ErrorMessage                                                                                                                                  |  |
      | PRN    | 12345678 | 5000.33       | idType must be either UTR or CRN.                                                                                                             |  |
      | CRN    |          | 5000.33       | idValue must be alphanumeric, and have a minimum length of 1 and a maximum length of 15.                                                      |  |
      | CRN    | 12345678 | 5000.333      | amountOwedDTT must be Numeric, positive, with at most 2 decimal places, and less than or equal to 13 characters, including the decimal place. |  |



