package scalanativecrossproject

import sbt._
import sbtcrossproject._
import scalanative.sbtplugin._

import scala.language.implicitConversions

trait ScalaNativeCross {
  val NativePlatform = scalanativecrossproject.NativePlatform

  implicit def NativeCrossProjectBuilderOps(
      builder: CrossProject.Builder): NativeCrossProjectOps =
    new NativeCrossProjectOps(builder.crossType(CrossType.Full))

  implicit class NativeCrossProjectOps(project: CrossProject) {
    def native: Project = project.projects(NativePlatform)

    def nativeSettings(ss: Def.SettingsDefinition*): CrossProject =
      nativeConfigure(_.settings(ss: _*))

    def nativeConfigure(transformer: Project => Project): CrossProject =
      project.configurePlatform(NativePlatform)(transformer)
  }
}
