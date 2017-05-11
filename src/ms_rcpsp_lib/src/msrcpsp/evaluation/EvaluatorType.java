package ms_rcpsp_lib.src.msrcpsp.evaluation;

/**
 * Enum containing all types of evaluators. Used mostly for
 * multi objective optimization, when multiple evaluators
 * are connected with a schedule. Allows to differentiate
 * which results came from which evaluator.
 */
public enum EvaluatorType {

    COST_EVALUATOR,
    DURATION_EVALUATOR,
    WEIGHTED_EVALUATOR,
    GENERATIONAL_DISTANCE_EVALUATOR,

}
