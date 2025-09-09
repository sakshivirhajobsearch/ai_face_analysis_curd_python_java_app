# python-backend/ai_model/face_expression_analysis.py

from deepface import DeepFace

def analyze_expression(image_path):
    try:
        result = DeepFace.analyze(img_path=image_path, actions=['emotion'])

        emotion_data = result[0]
        dominant_emotion = emotion_data['dominant_emotion']
        emotion_scores = emotion_data['emotion']

        return {
            "dominant_emotion": dominant_emotion,
            "emotion_scores": emotion_scores
        }

    except Exception as e:
        return {
            "error": str(e)
        }
