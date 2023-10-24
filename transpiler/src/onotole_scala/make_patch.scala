package onotole_scala

import com.github.difflib.{DiffUtils, UnifiedDiffUtils}

import java.nio.file.{Files, Paths}

@main def make_patches() =
  val tests: List[String] = Paths.get("lang_tests").toFile.listFiles(f =>
    f.isFile && f.getName.endsWith(".py")
  ).map(f => f.getName.substring(0, f.getName.length() - 3)).toList
  val modified = tests.filter(tn =>
    val dafnyDir = Paths.get("dafny")
    Files.exists(dafnyDir.resolve(tn + ".dfy"))
    && Files.exists(dafnyDir.resolve(tn + "_updated.dfy"))
  )
  modified.foreach(tn => calcDiff(tn))

def calcDiff(tn: String) =
  val dafnyDir = Paths.get("dafny")
  val origPath = dafnyDir.resolve(tn + ".dfy")
  val orig = Files.readAllLines(origPath)
  val newPath = dafnyDir.resolve(tn + "_updated.dfy")
  val newVersion = Files.readAllLines(newPath)
  val diff = DiffUtils.diff(orig, newVersion)
  val uniDiff = UnifiedDiffUtils.generateUnifiedDiff(null, null, orig, diff, 1)
  Files.write(Paths.get("lang_tests").resolve(tn + ".dfy.patch"), uniDiff)

@main def apply_patches() =
  val tests: List[String] = Paths.get("lang_tests").toFile.listFiles(f =>
    f.isFile && f.getName.endsWith(".py")
  ).map(f => f.getName.substring(0, f.getName.length() - 3)).toList
  val modified = tests.filter(tn =>
    Files.exists(Paths.get("lang_tests").resolve(tn + ".py"))
      && Files.exists(Paths.get("lang_tests").resolve(tn + ".dfy.patch"))
  )
  modified.foreach(tn => applyPatch(tn))

def applyPatch(tn: String) =
  val srcFile = Paths.get("dafny").resolve(tn + ".dfy")
  val origFile = Files.readAllLines(srcFile)
  val patchFile = Paths.get("lang_tests").resolve(tn + ".dfy.patch")
  val diffs = Files.readAllLines(patchFile)
  val patch = UnifiedDiffUtils.parseUnifiedDiff(diffs)
  val patchedLines = patch.applyFuzzy(origFile,1000)
  srcFile.toFile.renameTo(srcFile.getParent.resolve(tn + ".old.dfy").toFile)
  Files.write(srcFile, patchedLines)

