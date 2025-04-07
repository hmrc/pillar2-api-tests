@apiTests
Feature: Dynamic stubs scenarios for test organisation

  @testOrganisation
  Scenario Outline: Create, fetch, update and delete dynamic stub in submission api
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    When I make API call to create "<TestOrganisation>" using "<Endpoint>" with "<PLRID>"
    And I validate "TestOrganisation" "Requests" json schema for "OrganisationRequest"
    And I verify the response code is <StatusCode>
    Then I verify the response contains the following values:
      | Key                                      | ExpectedValue         |
      | pillar2Id                                | <PLRID>               |
      | organisation.orgDetails.organisationName | Test Organisation Ltd |
      | organisation.orgDetails.registrationDate | 2024-01-01            |
    Then I validate "TestOrganisation" "Response" json schema for "<JsonSchema>"

    When I make API call to create "<TestOrganisation>" using "<Endpoint>" with "<PLRID>"
    Then I verify the response code is 409
    And I validate "TestOrganisation" "Response" json schema for "OrganisationValidation"
    Then I verify the response contains the following values:
      | Key     | ExpectedValue                                       |
      | code    | 409                                                 |
      | message | Organisation with pillar2Id: <PLRID> already exists |

    When I make API call to get organisation details using "<Endpoint>" with "<PLRID>"
    Then I verify the response code is 200
    And I validate "TestOrganisation" "Requests" json schema for "OrganisationRequest"
    Then I verify the response contains the following values:
      | Key                                      | ExpectedValue         |
      | organisation.orgDetails.organisationName | Test Organisation Ltd |
      | pillar2Id                                | <PLRID>               |
      | organisation.orgDetails.registrationDate | 2024-01-01            |
    And I validate "TestOrganisation" "Response" json schema for "<JsonSchema>"

    When I make API call to update "New Organisation Ltd" using "<Endpoint>" with "<PLRID>"
    Then I verify the response code is 200
    And I validate "TestOrganisation" "Requests" json schema for "OrganisationRequest"
    Then I verify the response contains the following values:
      | Key                                      | ExpectedValue        |
      | organisation.orgDetails.organisationName | New Organisation Ltd |
      | pillar2Id                                | <PLRID>              |
      | organisation.orgDetails.registrationDate | 2024-01-01           |

    When I make API call to delete organisation using "<Endpoint>" with "<PLRID>"
    Then I verify the response code is 204
    When I make API call to delete organisation using "<Endpoint>" with "<PLRID>"
    Then I verify the response code is 404
    And I validate "TestOrganisation" "Response" json schema for "OrganisationValidation"
    Then I verify the response contains the following values:
      | Key     | ExpectedValue                                 |
      | code    | 404                                           |
      | message | Organisation not found for pillar2Id: <PLRID> |

    Examples:
      | UserType     | StatusCode | PLRID           | JsonSchema          | TestOrganisation      | Endpoint           |
      | Organisation | 201        | XMPLR0000000012 | OrganisationSuccess | Test Organisation Ltd | setup/organisation |
