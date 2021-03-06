package org.tron.core.services.http;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tron.api.GrpcAPI.WitnessList;
import org.tron.core.Wallet;


@Component
@Slf4j
public class ListWitnessesServlet extends HttpServlet {

  @Autowired
  private Wallet wallet;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    try {
      WitnessList reply = wallet.getWitnessList();
      if (reply != null) {
        response.getWriter().println(JsonFormat.printToString(reply));
      } else {
        response.getWriter().println("{}");
      }
    } catch (IOException e) {
      logger.debug("IOException: {}", e.getMessage());
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    doGet(request, response);
  }
}
