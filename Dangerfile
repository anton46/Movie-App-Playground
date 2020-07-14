github.dismiss_out_of_range_messages

# Make it more obvious that a PR is a work in progress and shouldn't be merged yet
warn("PR is classed as Work in Progress") if github.pr_title.include? "[WIP]"

# Warn when there is a big PR
warn("Big PR") if git.lines_of_code > 500

# Don't let testing shortcuts get into master by accident
fail("fdescribe left in tests") if `grep -r fdescribe specs/ `.length > 1
fail("fit left in tests") if `grep -r fit specs/ `.length > 1

# Jacoco
jacoco.minimum_project_coverage_percentage = 40 # default 0
jacoco.minimum_class_coverage_percentage = 75 # default 0
jacoco.files_extension = [".java"] # default [".kt", ".java"]
jacoco.report("app/build/reports/jacoco/jacocoDebugUnitTestReport/jacocoDebugUnitTestReport.xml", "http://jacoco-html-reports/")

# JUnit
tests = Hash.new
tests["app"]="app/build/test-results/*/*.xml"

# ktlint
checkstyle_format.base_path = Dir.pwd
checkstyle_format.report "app/build/reports/ktlint/ktlintMainSourceSetCheck.xml"

# AndroidLint
android_lint.report_file = "app/build/reports/lint/lint-results.xml"
android_lint.skip_gradle_task = true
android_lint.severity = "Error"
android_lint.lint(inline_mode: true)
