package hajimete.meet.config;

public enum JudgeComment {

    JUDGE_COMMENT_0(0, "なんていうか、、、これは選ばない方がいいかな。。"),
    JUDGE_COMMENT_1(1, "う〜ん。もうちょっと考えてみようか。"),
    JUDGE_COMMENT_2(2, "これでも良さそうだけど、もう少し他のも見てみよう！"),
    JUDGE_COMMENT_3(3, "まあこれでも大丈夫かな。"),
    JUDGE_COMMENT_4(4, "まあまあかな。欲を言うともう少し良さそうなのを探してもいいかな。"),
    JUDGE_COMMENT_5(5, "絶妙なお肉だね！大人たちも満足すると思うよ。"),
    JUDGE_COMMENT_6(6, "なかないい肉を見つけたね！バーベキューが楽しみだ♩"),
    JUDGE_COMMENT_7(7, "最高だね！これで大盛り上がり間違いなし！！"),
    JUDGE_COMMENT_8(8, "な、なんだこの肉は！この肉を持っていけば君はヒーロー間違いなし！");

    private int id;
    private String comment;

    JudgeComment(int id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public static String getComment(float score) {

//        if (0 <= score && score < 1) {
//            return JUDGE_COMMENT_0.comment;
//        } else if (1 <= score && score < 2) {
//            return JUDGE_COMMENT_1.comment;
//        } else if (2 <= score && score < 3) {
//            return JUDGE_COMMENT_2.comment;
//        } else if (3 <= score && score < 4) {
//            return JUDGE_COMMENT_3.comment;
//        } else if (4 <= score && score < 5) {
//            return JUDGE_COMMENT_4.comment;
//        } else if (5 <= score && score < 6) {
//            return JUDGE_COMMENT_5.comment;
//        } else if (6 <= score && score < 7) {
//            return JUDGE_COMMENT_6.comment;
//        } else if (7 <= score && score < 8) {
//            return JUDGE_COMMENT_7.comment;
//        } else if (8 <= score && score < 9) {
//            return JUDGE_COMMENT_8.comment;
//        } else {
//            return null;
//        }

        if (0 <= score && score < 3) {
            return JUDGE_COMMENT_0.comment;
        } else if (3 <= score && score < 6) {
            return JUDGE_COMMENT_3.comment;
        } else if (6 <= score && score < 9) {
            return JUDGE_COMMENT_8.comment;
        } else {
            return null;
        }
    }
}
