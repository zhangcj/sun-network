package org.tron.service;

import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.tron.common.config.Args;
import org.tron.common.exception.RpcConnectException;
import org.tron.common.utils.WalletUtil;
import org.tron.service.task.EventTask;
import org.tron.service.task.InitTask;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
@Slf4j(topic = "app")
public class App {

  private static int fixedThreads = 5;


  public static void main(String[] args) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    logger.info("start...");
    Args arg = Args.getInstance();
    try {
      arg.setParam(args);
    } catch (RpcConnectException e) {
      logger.error("failed to get sun token when setParam", e);
      System.exit(1);
    }

    String mainGateway = WalletUtil.encode58Check(arg.getSidechainGateway());
    String sideGateway = WalletUtil.encode58Check(arg.getSidechainGateway());

    (new InitTask(10)).batchProcessTxInDb();
    (new EventTask(mainGateway, sideGateway, fixedThreads)).processEvent();
    return;
  }
}