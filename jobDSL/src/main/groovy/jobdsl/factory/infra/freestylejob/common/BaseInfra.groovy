package jobdsl.factory.infra.freestylejob.common

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job


/**
 * Created by gal sasi on 25/11/2017.
 */
class BaseInfra {
    String name
    Integer numOfBuildsToKeep = 20
    Integer numOfArtifactToKeep = 10
    Integer timeoutInMinutes = 60
    String gitUrl = 'https://github.com/galsasi1989/trial.git'
    String stashUrl = 'https://github.com/galsasi1989/trial'
    String checkoutDirectory = 'trial'
    Boolean add_git_clone = false

    BaseInfra(String name) {
        this.name = name
    }

    Job build(DslFactory dslFactory) {
        dslFactory.freeStyleJob(this.name) {
            logRotator {
                numToKeep numOfBuildsToKeep
                artifactNumToKeep numOfArtifactToKeep
            }

            wrappers {
                preBuildCleanup()
                timeout {
                    absolute(timeoutInMinutes)
                }
                timestamps()
            }

			scm {
				git {
					remote {
						url(gitUrl)
					}
					branch('${SCM_BRANCH}')

					browser {
						stash(stashUrl)
					}

					extensions {
						relativeTargetDirectory(checkoutDirectory)
						cleanAfterCheckout()
						ignoreNotifyCommit()
					}
				}
			}
            //template_config(delegate, this.numOfBuildsToKeep, this.numOfArtifactToKeep, this.timeoutInMinutes, this.label, this.gitUrl, this.stashUrl, this.add_git_clone, this.pre_cleanUp_workspace, this.checkoutDirectory)
        }
    }
}


