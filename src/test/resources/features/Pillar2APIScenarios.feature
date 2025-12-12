#@apiTests @ignore
#Feature: Validate Pillar2 & Pillar2 Stubs responses
#
#  Scenario Outline: Verify the response for the stub requests
#    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
#    And I make API call to <RequestUrl> and <Endpoint> and <PLRID> and <StatusCode>
#    Then I verify the response code is <StatusCode>
#    Examples:
#      | UserType     | PLRID           | StatusCode | RequestUrl | Endpoint    |
#      | Organisation | XEPLR5555555555 | 201        | Stub       | UKTaxReturn |
#      | Organisation | XEPLR0123456400 | 400        | Stub       | UKTaxReturn |
#
#  Scenario Outline: Verify the response for the backend requests
#    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
#    And I make API call to <RequestUrl> and <Endpoint> and <PLRID> and <StatusCode>
#    Then I verify the response code is <StatusCode>
#    Examples:
#      | UserType     | PLRID           | StatusCode | RequestUrl | Endpoint             |
#      | Organisation | XMPLR0000000012 | 201        | Backend    | submit-uk-tax-return |
# #     | Organisation | XEPLR1066196422 | 422        | Backend    | submit-uk-tax-return |
