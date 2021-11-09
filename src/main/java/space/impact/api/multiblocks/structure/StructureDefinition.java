package space.impact.api.multiblocks.structure;

import space.impact.api.util.Vec3Impl;

import java.util.*;
import java.util.stream.Collectors;

public class StructureDefinition<T> implements IStructureDefinition<T> {
	private final Map<Character, IStructureElement<T>> elements;
	private final Map<String, String> shapes;
	private final Map<String, IStructureElement<T>[]> structures;
	
	private StructureDefinition(Map<Character, IStructureElement<T>> elements, Map<String, String> shapes, Map<String, IStructureElement<T>[]> structures) {
		this.elements   = elements;
		this.shapes     = shapes;
		this.structures = structures;
	}
	
	public static <B> Builder<B> builder() {
		return new Builder<>();
	}
	
	public Map<Character, IStructureElement<T>> getElements() {
		return elements;
	}
	
	public Map<String, String> getShapes() {
		return shapes;
	}
	
	public Map<String, IStructureElement<T>[]> getStructures() {
		return structures;
	}
	
	@Override
	public IStructureElement<T>[] getStructureFor(String name) {
		IStructureElement<T>[] elements = structures.get(name);
		if (elements == null)
			throw new NoSuchElementException(name);
		return elements;
	}
	
	public static class Builder<T> {
		private static final char A = '\uA000';
		private static final char B = '\uB000';
		private static final char C = '\uC000';
		public static boolean debug = false;
		private final Map<Vec3Impl, Character> navigates;
		private final Map<Character, IStructureElement<T>> elements;
		private final Map<String, String> shapes;
		private char d = '\uD000';
		
		private Builder() {
			navigates = new HashMap<>();
			elements  = new HashMap<>();
			shapes    = new HashMap<>();
		}
		
		public Map<Character, IStructureElement<T>> getElements() {
			return elements;
		}
		
		public Map<String, String> getShapes() {
			return shapes;
		}
		
		/**
		 * Casings go: 0 1 2 3 4 5 6 7 8 9 : ; < = > ?
		 * <br/>
		 * HatchAdders go: space ! " # $ % & ' ( ) *
		 *
		 * @param name           нелокализованное/кодовое название
		 * @param structurePiece сгенерированная или записанная структура - НЕ СОХРАНЯЙТЕ ЕЕ НИГДЕ, или, по крайней мере, установите их в null после этого
		 * @return текущий Builder
		 */
		@Deprecated
		public Builder<T> addShapeOldApi(String name, String[][] structurePiece) {
			StringBuilder builder = new StringBuilder();
			if (structurePiece.length > 0) {
				for (String[] strings : structurePiece) {
					if (strings.length > 0) {
						for (String string : strings) {
							for (int i = 0; i < string.length(); i++) {
								char ch = string.charAt(i);
								if (ch < ' ') {
									for (int b = 0; b < ch; b++) {
										builder.append(B);
									}
								} else if (ch > '@') {
									for (int a = '@'; a < ch; a++) {
										builder.append(A);
									}
								} else if (ch == '.') {
									builder.append(A);
								} else {
									builder.append(ch);
								}
							}
							builder.append(B);
						}
						builder.setLength(builder.length() - 1);
					}
					builder.append(C);
				}
				builder.setLength(builder.length() - 1);
			}
			int a = 0, b = 0, c = 0;
			for (int i = 0; i < builder.length(); i++) {
				char ch = builder.charAt(i);
				if (ch == A) {
					a++;
				} else if (ch == B) {
					a = 0;
					b++;
				} else if (ch == C) {
					a = 0;
					b = 0;
					c++;
				} else if (a != 0 || b != 0 || c != 0) {
					Vec3Impl vec3 = new Vec3Impl(a, b, c);
					Character navigate = navigates.get(vec3);
					if (navigate == null) {
						navigate = d++;
						navigates.put(vec3, navigate);
						addElement(navigate, StructureUtility.step(vec3));
					}
					builder.setCharAt(i - 1, navigate);
					a = 0;
					b = 0;
					c = 0;
				}
			}
			
			String built = builder.toString().replaceAll("[\\uA000\\uB000\\uC000]", "");
			
			if (built.contains("+")) {
				addElement('+', StructureUtility.notAir());
			}
			if (built.contains("-")) {
				addElement('-', StructureUtility.isAir());
			}
			shapes.put(name, built);
			return this;
		}
		
		/**
		 * Добавляет форму
		 * + является чем угодно, только не воздухом
		 * - проверяет воздух
		 * пробел - пропуск
		 * ~ также пропускается (но обозначает позицию контроллера, необязателен и логически является пробелом...)
		 * остальное необходимо определить
		 * <p>
		 * следующий {@link Character} - следующий блок (A)
		 * следующая {@link String} - следующая строка (B)
		 * следующая {@link String[]} - следующий фрагмент(C)
		 * <p>
		 * диапазон {@link Character} A000-FFFF зарезервирован для генерируемых пропусков
		 *
		 * @param name           нелокализованное/кодовое название
		 * @param structurePiece сгенерированная или записанная структура - НЕ СОХРАНЯЙТЕ ЕЕ НИГДЕ, или, по крайней мере, установите их в null после этого
		 * @return текущий Builder
		 */
		public Builder<T> addShape(String name, String[][] structurePiece) {
			StringBuilder builder = new StringBuilder();
			if (structurePiece.length > 0) {
				for (String[] strings : structurePiece) {
					if (strings.length > 0) {
						for (String string : strings) {
							builder.append(string).append(B);
						}
						builder.setLength(builder.length() - 1);
					}
					builder.append(C);
				}
				builder.setLength(builder.length() - 1);
			}
			int a = 0, b = 0, c = 0;
			for (int i = 0; i < builder.length(); i++) {
				char ch = builder.charAt(i);
				if (ch == ' ' || ch == '~' || ch == '.') {
					builder.setCharAt(i, A);
					ch = A;
				}
				if (ch == A) {
					a++;
				} else if (ch == B) {
					a = 0;
					b++;
				} else if (ch == C) {
					a = 0;
					b = 0;
					c++;
				} else if (a != 0 || b != 0 || c != 0) {
					Vec3Impl vec3 = new Vec3Impl(a, b, c);
					Character navigate = navigates.get(vec3);
					if (navigate == null) {
						navigate = d++;
						navigates.put(vec3, navigate);
						addElement(navigate, StructureUtility.step(vec3));
					}
					builder.setCharAt(i - 1, navigate);
					a = 0;
					b = 0;
					c = 0;
				}
			}
			
			String built = builder.toString().replaceAll("[\\uA000\\uB000\\uC000]", "");
			
			if (built.contains("+")) {
				addElement('+', StructureUtility.notAir());
			}
			if (built.contains("-")) {
				addElement('-', StructureUtility.isAir());
			}
			shapes.put(name, built);
			return this;
		}
		
		public Builder<T> addElement(Character name, IStructureElement<T> structurePiece) {
			elements.putIfAbsent(name, structurePiece);
			return this;
		}
		
		public IStructureDefinition<T> build() {
			Map<String, IStructureElement<T>[]> structures = compileStructureMap();
			if (debug) {
				return new StructureDefinition<>(new HashMap<>(elements), new HashMap<>(shapes), structures);
			} else {
				return structures::get;
			}
		}
		
		@SuppressWarnings("unchecked")
		private Map<String, IStructureElement<T>[]> compileElementSetMap() {
			Set<Integer> missing = findMissing();
			if (missing.isEmpty()) {
				return shapes.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().chars()
						.mapToObj(c -> (char) c).distinct().map(elements::get).toArray(IStructureElement[]::new)
				));
			} else {
				throw new RuntimeException("Missing Structure Element bindings for (chars as integers): " + Arrays.toString(missing.toArray()));
			}
		}
		
		@SuppressWarnings("unchecked")
		private Map<String, IStructureElement<T>[]> compileStructureMap() {
			Set<Integer> missing = findMissing();
			if (missing.isEmpty()) {
				return shapes.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().chars()
						.mapToObj(c -> elements.get((char) c)).toArray(IStructureElement[]::new)
				));
			} else {
				throw new RuntimeException("Missing Structure Element bindings for (chars as integers): " +
						Arrays.toString(missing.toArray()));
			}
		}
		
		private Set<Integer> findMissing() {
			return shapes.values().stream().flatMapToInt(CharSequence::chars).filter(i -> !elements.containsKey((char) i)).boxed().collect(Collectors.toSet());
		}
	}
}