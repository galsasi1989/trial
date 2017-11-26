package jobdsl.factory.infra.freestylejob

import jobdsl.factory.infra.freestylejob.common.BaseInfra
import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job

/**
 * Created by gal_sasi on 26/11/2017.
 */
class JobRunner extends BaseInfra {

    public static final String NAME_PREFIX = 'Split-Job-Runner'
    Boolean allowConcurrentBuild = true

    JobRunner() {
        super(NAME_PREFIX)
    }

    @Override
    Job build(DslFactory dslFactory) {
        Job bdiInfra = super.build(dslFactory)
        bdiInfra.with {
            parameters {
                stringParam('JobName')
                stringParam('SCM_BRANCH')
            }

            //concurrentBuild(allowConcurrentBuild)

            steps {
                downstreamParameterized {
                    trigger('${JobName}') {
                        block {
                            buildStepFailure('FAILURE')
                            failure('FAILURE')
                            unstable('UNSTABLE')
                        }
                        parameters {
                            predefinedProps(['SCM_BRANCH':'${SCM_BRANCH}'])
                        }
                    }
                }
            }
        }
    }
}
