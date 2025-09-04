package com.example.GoCafe.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class QuestionnaireService {

    // 질문, 답변 코드, 답변 텍스트, 태그 목록을 관리하는 내부 클래스
    public static class Question {
        private final String questionText;
        private final String questionCode;
        private final Map<String, String> answerOptions = new LinkedHashMap<>(); // <answer_code, answer_text>
        private final Map<String, List<String>> answerTags = new HashMap<>(); // <answer_code, List<tag_code>>

        public Question(String questionCode, String questionText) {
            this.questionCode = questionCode;
            this.questionText = questionText;
        }

        public void addAnswer(String answerCode, String answerText, String tagCode) {
            answerOptions.put(answerCode, answerText);
            answerTags.computeIfAbsent(answerCode, k -> new ArrayList<>()).add(tagCode);
        }

        public String getQuestionText() { return questionText; }
        public String getQuestionCode() { return questionCode; }
        public Map<String, String> getAnswerOptions() { return answerOptions; }
        public Map<String, List<String>> getAnswerTags() { return answerTags; }
    }

    private static final List<Question> QUESTIONNAIRE = new ArrayList<>();

    static {
        addQuestion("cond_energy", "지금 컨디션(에너지)은 어떤가요?", "low", "기운이 없고 편하게 쉬고 싶어요", "mood.cozy", "low", "기운이 없고 편하게 쉬고 싶어요", "noise.very_quiet", "low", "기운이 없고 편하게 쉬고 싶어요", "seat_space.sofa", "focus", "차분히 집중하고 싶어요", "purpose.work", "focus", "차분히 집중하고 싶어요", "noise.very_quiet", "focus", "차분히 집중하고 싶어요", "power_work.power_many", "chat", "가볍게 수다 떨고 싶어요", "purpose.chat", "chat", "가볍게 수다 떨고 싶어요", "noise.normal", "chat", "가볍게 수다 떨고 싶어요", "seat_space.table_2", "lively", "들뜨고 활기찬 분위기가 좋아요", "mood.modern", "lively", "들뜨고 활기찬 분위기가 좋아요", "noise.lively", "lively", "들뜨고 활기찬 분위기가 좋아요", "seat_space.table_4");
        addQuestion("companion", "누구와 가시나요?", "solo", "혼자", "purpose.solo", "solo", "혼자", "seat_space.single_seat", "friends", "친구/동료", "purpose.chat", "friends", "친구/동료", "seat_space.table_2", "date", "데이트", "purpose.date", "date", "데이트", "mood.cozy", "with_kid", "아이와 함께", "purpose.with_kid", "with_kid", "아이와 함께", "facility.stroller_friendly", "with_pet", "반려동물", "facility.pet_indoor", "with_pet", "반려동물", "facility.pet_outdoor");
        addQuestion("stay_time", "얼마나 머물 건가요?", "short", "금방 한 잔만", "waiting.none", "short", "금방 한 잔만", "seat_space.limit_120m", "long", "넉넉히 오래 머물래요", "seat_space.no_time_limit", "long", "넉넉히 오래 머물래요", "seat_space.wide_gap", "meeting", "회의/스터디 계획 있어요", "power_work.studyroom", "meeting", "회의/스터디 계획 있어요", "purpose.work");
        addQuestion("weather_air", "날씨/공기는 어때요?", "sunny", "햇살 좋은 곳이 좋아요", "light_view.natural_light", "sunny", "햇살 좋은 곳이 좋아요", "light_view.bright", "outdoor", "야외(테라스/루프탑)도 괜찮아요", "light_view.terrace", "outdoor", "야외(테라스/루프탑)도 괜찮아요", "light_view.rooftop", "cozy_in", "실내에서 아늑하게", "mood.cozy", "cozy_in", "실내에서 아늑하게", "light_view.dim", "vent", "비/미세먼지라 실내 환기 좋은 곳", "light_view.bright", "vent", "비/미세먼지라 실내 환기 좋은 곳", "mood.modern");
        addQuestion("flavor", "오늘 땡기는 맛(카페인/풍미)은?", "espresso", "진하고 묵직한 에스프레소 계열", "coffee.roastery", "espresso", "진하고 묵직한 에스프레소 계열", "coffee.specialty", "sweet_latte", "부드럽고 달달한 라떼/시그니처", "coffee.latte_good", "sweet_latte", "부드럽고 달달한 라떼/시그니처", "dessert_food.cake", "handdrip", "산미 있는 핸드드립", "coffee.handdrip", "handdrip", "산미 있는 핸드드립", "coffee.specialty", "noncoffee", "논커피(차, 에이드 등)", "coffee.noncoffee_variety", "noncoffee", "논커피(차, 에이드 등)", "coffee.tea_shop", "decaf", "디카페인이 좋아요", "coffee.decaf");
        addQuestion("density", "소리/분위기 밀도는?", "quiet", "거의 소리 없는 곳이 좋아요", "noise.very_quiet", "normal", "어느 정도 소음은 괜찮아요", "noise.normal", "lively", "북적여도 상관없어요", "noise.lively");
        addQuestion("purpose", "할 일/목적은?", "work", "집중 작업/공부가 목적", "purpose.work", "work", "집중 작업/공부가 목적", "power_work.notebook_ok", "work", "집중 작업/공부가 목적", "power_work.power_many", "photo", "사진 찍고 기록 남기고 싶음", "purpose.photo", "photo", "사진 찍고 기록 남기고 싶음", "photo_interior.photo_zone", "healing", "힐링/멍때리기", "purpose.healing", "healing", "힐링/멍때리기", "mood.nature");
        addQuestion("light_view_pref", "빛/뷰 취향은?", "bright", "밝고 채광 좋은 자리", "light_view.bright", "bright", "밝고 채광 좋은 자리", "light_view.natural_light", "dim", "은은하고 어두운 무드", "light_view.dim", "dim", "은은하고 어두운 무드", "mood.emotional", "scenic", "전망(야경/시티/강/산)이 좋아요", "light_view.night_view", "scenic", "전망(야경/시티/강/산)이 좋아요", "light_view.city_view", "scenic", "전망(야경/시티/강/산)이 좋아요", "light_view.river_view", "scenic", "전망(야경/시티/강/산)이 좋아요", "light_view.mountain_view");
        addQuestion("dessert_food_pref", "디저트/식사 느낌은?", "dessert", "케이크/쿠키 같은 디저트", "dessert_food.cake", "dessert", "케이크/쿠키 같은 디저트", "dessert_food.cookie", "brunch", "브런치/샌드위치도 좋음", "dessert_food.brunch", "brunch", "브런치/샌드위치도 좋음", "dessert_food.sandwich", "special_diet", "글루텐프리/비건 옵션 있으면 좋아요", "dessert_food.gluten_free", "special_diet", "글루텐프리/비건 옵션 있으면 좋아요", "dessert_food.vegan");
        addQuestion("budget", "예산 감각은?", "low", "합리적으로", "price.low", "mid", "보통", "price.mid", "high", "오늘은 기분 내볼래요", "price.premium");
        addQuestion("access", "접근/편의는?", "subway", "역세권이면 좋겠어요", "access.near_subway_5m", "bus", "버스 접근 괜찮은 곳", "access.bus_good", "parking", "주차가 꼭 필요해요", "facility.parking_free", "parking", "주차가 꼭 필요해요", "facility.parking_paid", "easy", "길 찾기 쉬운 곳", "access.easy_find");
        addQuestion("policy", "운영/정책 선호는?", "early", "이른 오전에도 열면 좋아요", "policy.open_early", "late", "늦게까지/심야 운영", "policy.late_night", "late", "늦게까지/심야 운영", "policy.open_24h", "no_kids", "노키즈존을 선호", "policy.no_kids", "photo_ok", "촬영 가능한 곳이면 좋음", "policy.photo_ok");
        addQuestion("interior_photo", "인테리어/포토 포인트", "neon_mirror", "네온사인/포토존/대형 거울 좋아요", "photo_interior.neon", "neon_mirror", "네온사인/포토존/대형 거울 좋아요", "photo_interior.photo_zone", "neon_mirror", "네온사인/포토존/대형 거울 좋아요", "photo_interior.big_mirror", "plants_nature", "식물 많은 자연 테마", "photo_interior.many_plants", "plants_nature", "식물 많은 자연 테마", "mood.nature", "book_art", "책/아트워크로 조용한 분위기", "photo_interior.book_cafe", "book_art", "책/아트워크로 조용한 분위기", "photo_interior.artwork");
    }

    private static void addQuestion(String questionCode, String questionText, String... data) {
        Question question = QUESTIONNAIRE.stream().filter(q -> q.getQuestionCode().equals(questionCode)).findFirst().orElse(null);
        if (question == null) {
            question = new Question(questionCode, questionText);
            QUESTIONNAIRE.add(question);
        }
        for (int i = 0; i < data.length; i += 3) {
            question.addAnswer(data[i], data[i+1], data[i+2]);
        }
    }

    public List<Question> getQuestionnaire() {
        return QUESTIONNAIRE;
    }

    public List<String> getTagsFromAnswers(Map<String, String> userAnswers) {
        Set<String> selectedTags = new HashSet<>();
        userAnswers.forEach((questionCode, answerCode) -> {
            if (answerCode != null && !answerCode.isEmpty()) {
                QUESTIONNAIRE.stream()
                        .filter(q -> q.getQuestionCode().equals(questionCode))
                        .findFirst()
                        .ifPresent(question -> {
                            List<String> tags = question.getAnswerTags().get(answerCode);
                            if (tags != null) {
                                selectedTags.addAll(tags);
                            }
                        });
            }
        });
        return new ArrayList<>(selectedTags);
    }
}