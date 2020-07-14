require 'nokogiri'

module Danger
  class DangerDetektParser < Plugin

    SEVERITY_LEVELS = %w(Info Warning Error Fatal)

    Issue = Struct.new('Issue', :file_name, :line_number, :column, :severity, :message)

    REPORT_FILE = 'detekt/output/detekt-checkstyle.xml'

    def read_issues_from_report(report_file)
      file = File.open(report_file)

      report = Nokogiri::XML(file) do |config|
        config.noblanks
      end
      report.xpath('//file')
    end

    def parse_issues(issues)
      issues.map do |e|
        file_name = e.attr('name')

        e.children.map do |e|
          line_number = e.attr('line')
          column = e.attr('column')
          severity = e.attr('severity')
          message = e.attr('message')
          Issue.new(file_name, line_number, column, severity, message)
        end
      end
    end

    def parse_results(results, heading)
      dir = "#{Dir.pwd}/"
      message = "#### #{heading} (#{results.count})\n\n"

      message << "| File | Reason |\n"
      message << "| ---- | ------ |\n"

      results.each do |r|
        filename = r.file_name.gsub(dir, "")
        line = r.line_number
        reason = r.message

        message << "#{github.html_link(filename + '#L' + line, {full_path: false})} | #{reason} \n"
      end

      message
    end

    def filter_issues(issues)
      target_files = (git.modified_files - git.deleted_files) + git.added_files
      issues.select do |e|
        target_files.include? e.file_name
      end
    end

    def report_count_of_issues_by_severity(filtered, severity)
      case severity
        when SEVERITY_LEVELS[1]
          fail("Detekt found: #{filtered.count} warnings")
        when SEVERITY_LEVELS[2]
          fail("Detekt found: #{filtered.count} errors")
        when SEVERITY_LEVELS[3]
          fail("Detekt found: #{filtered.count} fatal errors")
        else
          message("Detekt found: #{filtered.count} other issues")
      end
    end

    def message_for_issues(issues)
      message = "### Detekt found issues\n\n"

      SEVERITY_LEVELS.reverse.each do |level|
        filtered = issues.select do |issue|
          issue.severity.casecmp(level).zero?
        end
        report_count_of_issues_by_severity(filtered, level) unless filtered.empty?
        message << parse_results(filtered, level) unless filtered.empty?
      end

      message
    end

    def generate_report
      issues = read_issues_from_report(REPORT_FILE)
      parsed_issues = parse_issues(issues).flatten
      filtered_issues = filter_issues(parsed_issues)
      report = message_for_issues(filtered_issues)

      markdown(report) unless filtered_issues.empty?
    end
  end
end
