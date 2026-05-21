package com.szh.sms.mapper;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ResponsiveLayoutTest {

  @Test
  void loginUsesResponsiveCenteredLayout() throws IOException {
    String login = read("sms/src/components/login/login.vue");

    assertTrue(login.contains("display: flex"));
    assertTrue(login.contains("backgroundSize: 'cover'"));
    assertTrue(login.contains("clamp("));
  }

  @Test
  void shellLayoutProtectsPresentationScreens() throws IOException {
    String home = read("sms/src/components/home.vue");
    String header = read("sms/src/common/header.vue");
    String tabs = read("sms/src/common/tabs.vue");
    String table = read("sms/src/base/base-table.vue");

    assertTrue(home.contains("min-width: 960px"));
    assertTrue(header.contains("display: flex"));
    assertTrue(header.contains("@media (max-width: 1366px)"));
    assertTrue(tabs.contains("overflow-x: auto"));
    assertTrue(table.contains("flex-wrap: wrap"));
  }

  private String read(String path) throws IOException {
    return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
  }
}
