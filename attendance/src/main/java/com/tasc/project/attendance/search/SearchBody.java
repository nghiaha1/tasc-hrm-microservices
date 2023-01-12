package com.tasc.project.attendance.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchBody {
    private int page;

    private int pageSize;

    private long employeeId;

    private String startDate;

    private String endDate;

    private String sort;

    private int status;

    public static final class SearchBodyBuilder {
        private int page;

        private int pageSize;

        private long employeeId;

        private String startDate;

        private String endDate;

        private String sort;

        private int status;


        private SearchBodyBuilder() {
        }

        public static SearchBodyBuilder aSearchBody() {
            return new SearchBodyBuilder();
        }

        public SearchBodyBuilder withPage(int page) {
            this.page = page;
            return this;
        }

        public SearchBodyBuilder withPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public SearchBodyBuilder withEmployeeId(long employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public SearchBodyBuilder withStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public SearchBodyBuilder withEndDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public SearchBodyBuilder withSort(String sort) {
            this.sort = sort;
            return this;
        }

        public SearchBodyBuilder withStatus(int status) {
            this.status = status;
            return this;
        }

        public SearchBody build() {
            SearchBody searchBody = new SearchBody();
            searchBody.setPage(page);
            searchBody.setPageSize(pageSize);
            searchBody.setEmployeeId(employeeId);
            searchBody.setStartDate(startDate);
            searchBody.setEndDate(endDate);
            searchBody.setSort(sort);
            searchBody.setStatus(status);
            return searchBody;
        }
    }
}
