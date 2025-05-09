@apiTests
Feature: Validate UKTR Json schemas and Responses

  Scenario Outline: Verify Submit UKTR responses and validate schema for all user types
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    And I make API call to <RequestUrl> and <Endpoint> and <PLRID> and <StatusCode>
    Then I validate "UKTR" "Requests" json schema for "SubmitUKTR"
    When I verify the response code is <StatusCode>
    Then I validate "UKTR" "Response" json schema for "<JsonSchema>"
    Examples:
      | UserType     | StatusCode | PLRID           | JsonSchema     | RequestUrl     | Endpoint      |
      | Organisation | 201        | XMPLR0000000012 | UKTR_201       | Submission Api | uk-tax-return |
      | Organisation | 500        | XEPLR0123456500 | Error_Response | Submission Api | uk-tax-return |
      | Organisation | 400        | XEPLR0000000400 | Error_Response | Submission Api | uk-tax-return |
      | Organisation | 401        | XEPLR0000000400 | Error_Response | Submission Api | uk-tax-return |
      | Agent        | 201        | XEPLR5555551126 | UKTR_201       | Submission Api | uk-tax-return |
      | Agent        | 500        | XEPLR0123456400 | Error_Response | Submission Api | uk-tax-return |
      | Agent        | 400        | XEPLR0000000400 | Error_Response | Submission Api | uk-tax-return |
      | Agent        | 401        | XEPLR0000000400 | Error_Response | Submission Api | uk-tax-return |
      | Individual   | 403        | XEPLR0000000400 | Error_Response | Submission Api | uk-tax-return |

  Scenario Outline: Verify Amend UKTR responses and validate schema
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    And I make API call to <RequestUrl> and <Endpoint> and <PLRID> and <StatusCode>
    Then I validate "UKTR" "Requests" json schema for "SubmitUKTR"
    When I verify the response code is <StatusCode>
    Then I validate "UKTR" "Response" json schema for "<JsonSchema>"
    Examples:
      | UserType     | StatusCode | PLRID           | JsonSchema     | RequestUrl | Endpoint      |
      | Organisation | 200        | XMPLR0000000012 | AmendUKTR_200  | Amend UKTR | uk-tax-return |
      | Organisation | 500        | XEPLR0500000000 | Error_Response | Amend UKTR | uk-tax-return |
      | Organisation | 400        | XEPLR0000000400 | Error_Response | Amend UKTR | uk-tax-return |
      | Organisation | 401        | XEPLR0000000400 | Error_Response | Amend UKTR | uk-tax-return |
      | Individual   | 403        | XEPLR0000000400 | Error_Response | Amend UKTR | uk-tax-return |

  Scenario Outline: Verify Submit UKTR Nil Return responses and validate schema
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    And I make API call to <RequestUrl> and <Endpoint> and <PLRID> and <StatusCode>
    Then I validate "UKTR" "Requests" json schema for "SubmitUKTRNilReturn"
    When I verify the response code is <StatusCode>
    Then I validate "UKTR" "Response" json schema for "<JsonSchema>"
    Examples:
      | UserType     | StatusCode | PLRID           | JsonSchema     | RequestUrl                | Endpoint      |
      | Organisation | 201        | XMPLR0000000012 | NilReturn_201  | Submission Nil Return Api | uk-tax-return |
      | Organisation | 500        | XEPLR0123456500 | Error_Response | Submission Nil Return Api | uk-tax-return |
      | Organisation | 400        | XEPLR0000000400 | Error_Response | Submission Nil Return Api | uk-tax-return |
      | Organisation | 401        | XEPLR0000000400 | Error_Response | Submission Nil Return Api | uk-tax-return |
      | Individual   | 403        | XEPLR0000000400 | Error_Response | Submission Nil Return Api | uk-tax-return |

  Scenario Outline: Verify the error code & message for the invalid UKTR requests
    Given I have generated a bearer token for an <UserType> and <Enrolment> and <StatusCode>
    And I make API call to PLR UKTR with "<PLRID>" for "<ErrorCode>"
    Then I verify the response code is <StatusCode> and <ErrorCode> and <ErrorMessage>
    Examples:
      | UserType     | Enrolment      | StatusCode | ErrorCode          | ErrorMessage         | PLRID           |
      | Organisation | with enrolment | 400        | INVALID_JSON       | Invalid JSON payload | XMPLR0012345674 |
      | Organisation | with enrolment | 400        | EMPTY_REQUEST_BODY | Empty request body   | XMPLR0012345674 |