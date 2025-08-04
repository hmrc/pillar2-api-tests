@apiTests
Feature: Validate Obligations & Submission History Json schema and Responses

  @obligationSubmissionApiBackend
  Scenario Outline: Verify Obligations & Submission History responses and validate schema for backend
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>

    When I make API call to delete organisation using "setup/organisation" with "<PLRID>"

    When I make API call to create "<DomesticFlag>" "<TestOrganisation>" using "setup/organisation" with "<PLRID>"
    And I validate "TestOrganisation" "Requests" json schema for "OrganisationRequest"
    And I verify the response code is 201
    Then I verify the response contains the following values:
      | Key                                      | ExpectedValue         |
      | pillar2Id                                | <PLRID>               |
      | organisation.orgDetails.organisationName | Test Organisation Ltd |
      | organisation.orgDetails.registrationDate | 2024-01-01            |

    Then I validate "TestOrganisation" "Response" json schema for "OrganisationSuccess"

    And I make get API call to URL <RequestUrl> and <Parameters> and <PLRID>
    When I verify the response code is <StatusCode>
    Then I validate "Obligation" "Response" json schema for "<JsonSchema>"
    Examples:
      | UserType     | StatusCode | PLRID           | JsonSchema                              | RequestUrl                             | Parameters            | TestOrganisation      |
      | Organisation | 200        | XMPLR0000000012 | Obligations_And_Submission_200          | Obligations and Submission Api backend | 2024-01-30/2024-12-31 | Test Organisation Ltd |
      | Organisation | 500        | XMPLR0000000012 | ObligationsAndSubmission_Error_Response | Obligations and Submission Api backend | 2024-01-29/2024-12-35 | Test Organisation Ltd |

  @obligationSubmissionApi
  Scenario Outline: Verify Obligations & Submission History responses and validate schema submission api
    Given I have generated a bearer token for an <UserType> and <PLRID> and <StatusCode>
    When I make API call to delete organisation using "setup/organisation" with "<PLRID>"

    When I make API call to create "<DomesticFlag>" "<TestOrganisation>" using "setup/organisation" with "<PLRID>"
    And I validate "TestOrganisation" "Requests" json schema for "OrganisationRequest"
    And I verify the response code is 201
    Then I verify the response contains the following values:
      | Key                                      | ExpectedValue         |
      | pillar2Id                                | <PLRID>               |
      | organisation.orgDetails.organisationName | Test Organisation Ltd |
      | organisation.orgDetails.registrationDate | 2024-01-01            |

    Then I validate "TestOrganisation" "Response" json schema for "OrganisationSuccess"

    And I make get API call to URL <RequestUrl> and <Parameters> and <PLRID>
    When I verify the response code is <StatusCode>
    Then I validate "Obligation" "Response" json schema for "<JsonSchema>"
    Examples:
      | UserType     | StatusCode | PLRID           | JsonSchema                              | RequestUrl                     | Parameters                            | TestOrganisation      |
      | Organisation | 200        | XMPLR0000000012 | Obligations_And_Submission_200          | Obligations and Submission Api | fromDate=2024-01-01&toDate=2024-12-31 | Test Organisation Ltd |
      | Organisation | 400        | XMPLR0000000012 | ObligationsAndSubmission_Error_Response | Obligations and Submission Api | fromDate=2024-01-01&toDate=2024-12-32 | Test Organisation Ltd |