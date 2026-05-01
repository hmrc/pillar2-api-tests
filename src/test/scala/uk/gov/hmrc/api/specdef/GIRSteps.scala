package uk.gov.hmrc.api.specdef

import uk.gov.hmrc.api.pages.{StateStoragePage, UKTRPage}

object GIRSteps {


  def givenIMakeAPICallTo(requestApi: String, endpoint: String, pillarID: String, statusCode: String): Unit =
    StateStoragePage.setResponseCode(UKTRPage.sendUKTRRequest(requestApi, endpoint, pillarID, statusCode))

}
