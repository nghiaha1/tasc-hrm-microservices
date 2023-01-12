package com.tasc.project.user.search;

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
    private int limit;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String nameProduct;
    private String startPrice;
    private String endPrice;
    private String search;
    private String sort;
    private String sortPrice;
    private String start;
    private String end;
    private int status;
    private Long cateId;
    private Long roleId;
    private String gender;
    private Long originId;
    private Long userPostId;
    private Long id;

        public static final class SearchBodyBuilder {
            private int page;
            private int limit;
            private Long id;
            private String sortPrice;
            private int status;
            private String username;
            private String fullName;
            private String email;
            private String phone;
            private String nameProduct;
            private String search;
            private String startPrice;
            private String endPrice;
            private String sort;
            private String start;
            private String end;
            private Long cateId;
            private Long roleId;
            private String gender;
            private Long originId;
            private Long userPostId;

            private SearchBodyBuilder() {
            }

            public static SearchBodyBuilder aSearchBody() {
                return new SearchBodyBuilder();
            }

            public SearchBodyBuilder withPage(int page) {
                this.page = page;
                return this;
            }

            public SearchBodyBuilder withId(Long id){
                this.id = id;
                return this;
            }
            public SearchBodyBuilder withOriginId(Long originId){
                this.originId = originId;
                return this;
            }
            public SearchBodyBuilder withUserPostId(Long userPostId){
                this.userPostId = userPostId;
                return this;
            }
            public SearchBodyBuilder withStatus(int status) {
                this.status = status;
                return this;
            }

            public SearchBodyBuilder withUsername(String username) {
                this.username = username;
                return this;
            }
            public SearchBodyBuilder withSearchBody(String search) {
                this.search = search;
                return this;
            }

            public SearchBodyBuilder withFullName(String fullName) {
                this.fullName = fullName;
                return this;
            }

            public SearchBodyBuilder withEmail(String email) {
                this.email = email;
                return this;
            }

            public SearchBodyBuilder withCateId(Long cateId) {
                this.cateId = cateId;
                return this;
            }
            public SearchBodyBuilder withGender(String gender) {
                this.gender = gender;
                return this;
            }

            public SearchBodyBuilder withStartPrice(String startPrice) {
                this.startPrice = startPrice;
                return this;
            }

            public SearchBodyBuilder withEndPrice(String endPrice) {
                this.endPrice = endPrice;
                return this;
            }
            public SearchBodyBuilder withRoleId(Long roleId) {
                this.roleId = roleId;
                return this;
            }

            public SearchBodyBuilder withLimit(int limit) {
                this.limit = limit;
                return this;
            }

            public SearchBodyBuilder withPhone(String phone) {
                this.phone = phone;
                return this;
            }

            public SearchBodyBuilder withNameProduct(String nameProduct) {
                this.nameProduct = nameProduct;
                return this;
            }

            public SearchBodyBuilder withSort(String sort) {
                this.sort = sort;
                return this;
            }

            public SearchBodyBuilder withSortPrice(String sortPrice) {
                this.sortPrice = sortPrice;
                return this;
            }

            public SearchBodyBuilder withStart(String start) {
                this.start = start;
                return this;
            }

            public SearchBodyBuilder withEnd(String end) {
                this.end = end;
                return this;
            }



            public SearchBody build() {
                SearchBody searchBody = new SearchBody();
                searchBody.setPage(page);
                searchBody.setLimit(limit);
                searchBody.setUsername(username);
                searchBody.setEmail(email);
                searchBody.setFullName(fullName);
                searchBody.setSearch(search);
                searchBody.setGender(gender);
                searchBody.setStatus(status);
                searchBody.setCateId(cateId);
                searchBody.setRoleId(roleId);
                searchBody.setStartPrice(startPrice);
                searchBody.setEndPrice(endPrice);
                searchBody.setPhone(phone);
                searchBody.setNameProduct(nameProduct);
                searchBody.setSort(sort);
                searchBody.setStart(start);
                searchBody.setEnd(end);
                searchBody.setOriginId(originId);
                searchBody.setUserPostId(userPostId);
                searchBody.setSortPrice(sortPrice);
                searchBody.setId(id);
                return searchBody;
            }
        }
    }
