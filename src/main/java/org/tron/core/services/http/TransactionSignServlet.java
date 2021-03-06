package org.tron.core.services.http;

import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tron.core.Wallet;
import org.tron.core.capsule.TransactionCapsule;
import org.tron.core.services.http.JsonFormat.ParseException;
import org.tron.protos.Protocol.TransactionSign;


@Component
@Slf4j
public class TransactionSignServlet extends HttpServlet {

  @Autowired
  private Wallet wallet;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) {

  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    try {
      String contract = request.getReader().lines()
          .collect(Collectors.joining(System.lineSeparator()));
      TransactionSign.Builder build = TransactionSign.newBuilder();
      JsonFormat.merge(contract, build);
      TransactionCapsule reply = wallet.getTransactionSign(build.build());
      if (reply != null) {
        response.getWriter().println(JsonFormat.printToString(reply.getInstance()));
      } else {
        response.getWriter().println("{}");
      }
    } catch (ParseException e) {
      logger.debug("ParseException: {}", e.getMessage());
    } catch (IOException e) {
      logger.debug("IOException: {}", e.getMessage());
    }
  }
}
