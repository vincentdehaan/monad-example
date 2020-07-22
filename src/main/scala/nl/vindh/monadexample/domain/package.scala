package nl.vindh.monadexample

import shapeless.tag.@@

package object domain {
  trait NameTag
  trait AgeTag
  trait YearTag
  trait YearsTag
  trait CompanyNameTag
  trait SchoolNameTag
  trait CourseTitleTag
  trait RoleNameTag
  trait EducationIdTag
  trait GithubHandleTag
  trait PullRequestTag

  type Name = String @@ NameTag
  type Age = Int @@ AgeTag
  type Year = Int @@ YearTag
  type Years = Int @@ YearsTag
  type CompanyName = String @@ CompanyNameTag
  type SchoolName = String @@ SchoolNameTag
  type CourseTitle = String @@ CourseTitleTag
  type RoleName = String @@ RoleNameTag
  type EducationId = String @@ EducationIdTag
  type GithubHandle = String @@ GithubHandleTag
  type PullRequest = String @@ PullRequestTag
}
