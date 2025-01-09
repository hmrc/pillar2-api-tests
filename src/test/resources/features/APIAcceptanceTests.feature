@apiTests
Feature: UKTR and BTN Scenarios

#TODO : PLRID can be added in future as per the requirement.
  Scenario Outline: verify submit UKTR response
    Given I have generated a bearer token for an <UserType> and <PLRID>
    And I make API call to <RequestUrl> and <Endpoint> and <PLRID>
    Then I validate request json schema for "jsonSchema/uktrSchema/Requests/SubmitUKTR_Request.json"
    When I verify response code is <StatusCode>
    Then I validate response json schema for "<JsonSchema>"
    Examples:
      | UserType     | StatusCode | PLRID           | JsonSchema                                              | RequestUrl     | Endpoint      |
      | Organisation | 201        | XMPLR0000000012 | jsonSchema/uktrSchema/Response/UKTR_201.json            | Submission Api | uk-tax-return |
      | Organisation | 500        | XEPLR0123456500 | jsonSchema/uktrSchema/Response/UKTR_Error_Response.json | Submission Api | uk-tax-return |

  Scenario Outline: verify submit UKTR nil return response
    Given I have generated a bearer token for an <UserType> and <PLRID>
    And I make API call to <RequestUrl> and <Endpoint> and <PLRID>
    Then I validate request json schema for "jsonSchema/uktrSchema/Requests/SubmitUKTRNilReturn_Request.json"
    When I verify response code is <StatusCode>
    Then I validate response json schema for "<JsonSchema>"
    Examples:
      | UserType     | StatusCode | PLRID           | JsonSchema                                              | RequestUrl                | Endpoint      |
      | Organisation | 201        | XMPLR0000000012 | jsonSchema/uktrSchema/Response/UKTR_201.json            | Submission Nil Return Api | uk-tax-return |
      | Organisation | 500        | XEPLR0123456500 | jsonSchema/uktrSchema/Response/UKTR_Error_Response.json | Submission Nil Return Api | uk-tax-return |

  Scenario Outline: verify submission api for BTN
    Given I have generated a bearer token for an <UserType> and <PLRID>
    And I make API call to <RequestUrl> and <Endpoint> and <PLRID>
    Then I validate request json schema for "jsonSchema/uktrSchema/Requests/SubmissionApiBTN_Request.json"
    When I verify response code is <StatusCode>
    Then I validate response json schema for "<JsonSchema>"
    Examples:
      | UserType     | StatusCode | PLRID           | JsonSchema                                              | RequestUrl         | Endpoint                     |
      | Organisation | 201        | XMPLR0000000012 | jsonSchema/uktrSchema/Response/UKTR_201.json            | Submission Api BTN | below-threshold-notification |
      | Organisation | 500        | XEPLR0123456500 | jsonSchema/uktrSchema/Response/UKTR_Error_Response.json | Submission Api BTN | below-threshold-notification |