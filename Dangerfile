# Sometimes it's a README fix, or something like that - which isn't relevant for
# including in a project's CHANGELOG for example
declared_trivial = github.pr_title.include? "#trivial"

# Make it more obvious that a PR is a work in progress and shouldn't be merged yet
warn("PR is classed as Work in Progress") if github.pr_title.include? "[WIP]"

# Warn when there is a big PR
warn("Big PR") if git.lines_of_code > 500

# Don't let testing shortcuts get into master by accident
fail("fdescribe left in tests") if `grep -r fdescribe specs/ `.length > 1
fail("fit left in tests") if `grep -r fit specs/ `.length > 1

jacoco.minimum_project_coverage_percentage = 50 # default 0
jacoco.minimum_package_coverage_map = { # optional (default is empty)
  'com/package/' => 55,
  'com/package/more/specific/' => 15
}
jacoco.minimum_class_coverage_map = { # optional (default is empty)
  'com/package/more/specific/ClassName' => 15
}
jacoco.minimum_class_coverage_percentage = 75 # default 0
jacoco.files_extension = [".java"] # default [".kt", ".java"]
jacoco.report("path/to/jacoco.xml", "http://jacoco-html-reports/")
