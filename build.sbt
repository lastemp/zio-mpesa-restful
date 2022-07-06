scalaVersion := "2.13.8"
organization := "com.lastemp"
name         := "zio-mpesa-restful"

libraryDependencies ++= Seq(
  "dev.zio"       %% "zio"            % "2.0.0-RC5",
  "dev.zio"       %% "zio-json"       % "0.3.0-RC7",
  "io.d11"        %% "zhttp"          % "2.0.0-RC7",
  "io.getquill"   %% "quill-zio"      % "3.17.0-RC3",
  "io.getquill"   %% "quill-jdbc-zio" % "3.17.0-RC3",
  "io.github.kitlangton" %% "zio-magic"  % "0.3.12",
  "org.postgresql" %  "postgresql"    % "42.4.0"
)