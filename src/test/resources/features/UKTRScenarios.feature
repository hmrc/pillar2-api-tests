@apiTests @idGroup
Feature: UKTR Scenarios

  Scenario Outline: Verify that the Group has not enrolled
    Given I have generated a bearer token for an <UserType> and <Enrolment>
    And I make API call to PLR UKTR
    Then I verify the response code is <StatusCode>
    Examples:
      | UserType     | Enrolment         | StatusCode |
      | Organisation | with enrolment    | 201        |
      | Organisation | without enrolment | 401        |

  Scenario Outline: Verify that user has sent a authenticated UKTR request to verify UKTR submission
    Given I have generated a bearer token for an <UserType> and <PLRID>
    And I make API call to <RequestUrl> and <Endpoint> and <PLRID>
    Then I verify the response code is <StatusCode>
    Examples:
      | UserType     | PLRID           | StatusCode | RequestUrl     | Endpoint      |
      | Organisation | XEPLR5555555555 | 201        | Submission Api | uk-tax-return |
      | Organisation | XEPLR0123456400 | 500        | Submission Api | uk-tax-return |

#to do : PLRID can be added in future as per the requirement.
  Scenario Outline: verify error codes for uktr submission
    Given I have generated a bearer token for an <UserType> and <PLRID>
    And I make API call to <RequestUrl> and <Endpoint> and <PLRID>
    Then I validate request json schema for "jsonSchema/uktrSchema/Requests/SubmitUKTR_Request.json"
    When I verify response code is <StatusCode>
    Then I validate response json schema for "<JsonSchema>"
    Examples:
      | UserType     | StatusCode | PLRID           | JsonSchema                                   | RequestUrl     | Endpoint      |
  #   | Organisation | 422        | XEPLR0000000422 | jsonSchema/uktrSchema/Response/UKTR_422.json | Submission Api |uk-tax-return|
  #   | Organisation | 400        | XEPLR0000000400 | jsonSchema/uktrSchema/Response/UKTR_400.json | Submission Api |uk-tax-return|
      | Organisation | 500        | XEPLR0000000500 | jsonSchema/uktrSchema/Response/UKTR_500.json | Submission Api | uk-tax-return |
      | Organisation | 201        | XMPLR0000000012 | jsonSchema/uktrSchema/Response/UKTR_201.json | Submission Api | uk-tax-return |

  Scenario Outline: Verify the error code and error message for the invalid UKTR requests
    Given I have generated a bearer token for an <UserType> and <Enrolment>
    And I make API call to PLR UKTR with <ErrorCode>
    Then I verify the response code is <StatusCode> and <ErrorCode> and <ErrorMessage>
    Examples:
      | UserType     | Enrolment         | StatusCode | ErrorCode | ErrorMessage          |
      | Organisation | with enrolment    | 400        | 001       | Invalid JSON Payload  |
      | Organisation | with enrolment    | 400        | 002       | Empty body in request |
      | Organisation | without enrolment | 401        | 003       | Not authorized        |

  Scenario Outline: Verify the response for the external stub requests
    Given I have generated a bearer token for an <UserType> and <PLRID>
    And I make API call to <RequestUrl> and <Endpoint> and <PLRID>
    Then I verify the response code is <StatusCode>
    Examples:
      | UserType     | PLRID           | StatusCode | RequestUrl    | Endpoint    |
      | Organisation | XMPLR0000000012 | 201        | External stub | UKTaxReturn |
      | Organisation | XEPLR0000000400 | 400        | External stub | UKTaxReturn |
      | Organisation | XEPLR0000000500 | 500        | External stub | UKTaxReturn |
      | Organisation | XEPLR0000000422 | 422        | External stub | UKTaxReturn |

  Scenario Outline: Verify the response for the stub requests
    Given I have generated a bearer token for an <UserType> and <PLRID>
    And I make API call to <RequestUrl> and <Endpoint> and <PLRID>
    Then I verify the response code is <StatusCode>
    Examples:
      | UserType     | PLRID           | StatusCode | RequestUrl | Endpoint    |
      | Organisation | XMPLR0000000012 | 201        | Stub       | UKTaxReturn |
      | Organisation | XEPLR1066196400 | 400        | Stub       | UKTaxReturn |

  Scenario Outline: Verify the response for the backend requests
    Given I have generated a bearer token for an <UserType> and <PLRID>
    And I make API call to <RequestUrl> and <Endpoint> and <PLRID>
    Then I verify the response code is <StatusCode>
    Examples:
      | UserType     | PLRID           | StatusCode | RequestUrl | Endpoint             |
      | Organisation | XMPLR0000000012 | 200        | Backend    | submit-uk-tax-return |
      | Organisation | XEPLR1066196422 | 422        | Backend    | submit-uk-tax-return |